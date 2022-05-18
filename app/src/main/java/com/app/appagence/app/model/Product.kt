package com.app.appagence.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val avatar: String? = null,
    val name: String? = null,
    val description: String? = null
) : Parcelable
