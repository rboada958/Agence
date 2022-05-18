package com.app.appagence.ui.details.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.app.appagence.R
import com.app.appagence.databinding.ConfirmPurchaseLayoutBinding

class ConfirmPurchaseDialog(private val listener: OnConfirmPurchaseListener) : DialogFragment() {

    private var _binding: ConfirmPurchaseLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConfirmPurchaseLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(dialog?.window!!) {
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

        binding.accept.setOnClickListener {
            listener.confirm()
            dismiss()
        }

        binding.cancel.setOnClickListener {
            dismiss()
        }
    }

    interface OnConfirmPurchaseListener {
        fun confirm()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}