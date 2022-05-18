package com.app.appagence.ui.home

import com.app.appagence.app.data.LocalDataStore
import com.app.appagence.app.model.Product
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val localDataStore: LocalDataStore
) : HomeRepository {

    override suspend fun getProductList(): MutableList<Product> {
        return localDataStore.getProductList()
    }
}