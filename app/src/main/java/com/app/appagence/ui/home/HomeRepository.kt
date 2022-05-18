package com.app.appagence.ui.home

import com.app.appagence.app.model.Product

interface HomeRepository {

    suspend fun getProductList() : MutableList<Product>
}