package com.app.appagence.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment {

    protected var fragmentView: View? = null


    constructor() : super()
    constructor(@LayoutRes layout: Int) : super(layout)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = super.onCreateView(inflater, container, savedInstanceState)
        return fragmentView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentView = null
    }
}