package com.app.appagence.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.appagence.R
import com.app.appagence.app.model.Product
import com.app.appagence.databinding.FragmentHomeBinding
import com.app.appagence.ui.home.adapter.HomeAdapter
import com.app.appagence.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeAdapter.OnHomeClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val adapter by lazy {
        HomeAdapter(mutableListOf(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getProductList()

        binding.productList.adapter = adapter

        homeViewModel.productListLive.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { list ->
                adapter.addItems(list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductClicked(item: Product, position: Int) {
        val args = Bundle()
        args.putParcelable("product", item)
        findNavController().navigate(
            R.id.action_nav_home_to_detailsFragment,
            args
        )
    }
}