package com.app.appagence.ui.details

import android.Manifest
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.test.core.app.ApplicationProvider
import com.app.appagence.R
import com.app.appagence.app.model.Product
import com.app.appagence.databinding.FragmentDetailsBinding
import com.app.appagence.ui.details.dialog.ConfirmPurchaseDialog
import com.app.appagence.ui.details.dialog.SuccessPurchaseDialog
import com.app.appagence.utils.base.loadRect
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task


class DetailsFragment : Fragment(), OnMapReadyCallback,
    ConfirmPurchaseDialog.OnConfirmPurchaseListener,
    SuccessPurchaseDialog.OnSuccessPurchaseListener {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap
    private var locationRequest: LocationRequest? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLocation()
        createMaps()
        showDetails(requireArguments().getParcelable("product")!!)

        binding.buttonBuy.setOnClickListener {
            ConfirmPurchaseDialog(this).show(childFragmentManager, null)
        }
    }

    private fun setLocation() {
        locationRequest = LocationRequest.create()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.interval = 5000
        locationRequest!!.fastestInterval = 2000
    }

    private fun createMaps() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun showDetails(product: Product) {
        binding.avatar.loadRect(product.avatar)
        binding.name.text = product.name
        binding.description.text = product.description
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        currentLocation()
    }

    private fun currentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (isGPSEnabled()) {
                    LocationServices.getFusedLocationProviderClient(requireContext())
                        .requestLocationUpdates(locationRequest!!, object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                super.onLocationResult(locationResult)
                                context?.let {
                                    LocationServices.getFusedLocationProviderClient(it)
                                        .removeLocationUpdates(this)
                                }
                                if (locationResult.locations.size > 0) {
                                    val index = locationResult.locations.size - 1
                                    latitude = locationResult.locations[index].latitude
                                    longitude = locationResult.locations[index].longitude
                                    createMarker(latitude, longitude)
                                }
                            }
                        }, Looper.getMainLooper())
                } else {
                    turnOnGPS()
                }
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
    }

    private fun turnOnGPS() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true)
        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(ApplicationProvider.getApplicationContext())
                .checkLocationSettings(builder.build())
        result.addOnCompleteListener {
            try {
                Toast.makeText(requireContext(), "GPS is already tured on", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(requireActivity(), 2)
                    } catch (ex: SendIntentException) {
                        ex.printStackTrace()
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
                }
            }
        }
    }

    private fun isGPSEnabled(): Boolean {
        var locationManager: LocationManager? = null
        if (locationManager == null) {
            locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        }
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun createMarker(latitude: Double?, longitude: Double?) {
        val coordinates = LatLng(latitude!!, longitude!!)
        val market = MarkerOptions().position(coordinates)
        map.addMarker(market)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 100f),
            5000,
            null
        )
    }

    override fun confirm() {
        SuccessPurchaseDialog(this).show(childFragmentManager, null)
    }

    override fun success() {
        findNavController().navigateUp()
    }
}