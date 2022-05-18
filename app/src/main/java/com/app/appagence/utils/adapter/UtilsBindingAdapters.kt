package com.app.appagence.utils.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.appagence.utils.base.lazyLoading
import com.app.appagence.utils.base.loadRect

object UtilsBindingAdapters {

    @BindingAdapter("loadUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        url?.let { imageView.lazyLoading(it) }
    }
}