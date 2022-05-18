package com.app.appagence.ui.details.dialog

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.app.appagence.R
import com.app.appagence.databinding.SuccessPurchaseLayoutBinding

class SuccessPurchaseDialog(private val listener: OnSuccessPurchaseListener) : DialogFragment() {

    private var _binding: SuccessPurchaseLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SuccessPurchaseLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(dialog?.window!!) {
            isCancelable = false
            setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.custom_dialog_background
                )
            )
        }

        Handler(Looper.getMainLooper()).postDelayed({
            listener.success()
            dismiss()
        }, 2000)

    }

    interface OnSuccessPurchaseListener {
        fun success()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}