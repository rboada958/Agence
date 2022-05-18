package com.app.appagence.utils.base

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


fun ImageView.loadRect(urlImage: String?) {
    Glide.with(context)
        .load(urlImage)
        .centerCrop()
        .into(this)
}

fun ImageView.lazyLoading(urlImage: String?) {
    Glide.with(this)
        .load(urlImage)
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        })
        .into(this)
}