package com.app.appagence.app.usecase

import com.app.appagence.ui.home.HomeRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repository: HomeRepository) {

    suspend operator fun invoke() =
        repository.getProductList()
}