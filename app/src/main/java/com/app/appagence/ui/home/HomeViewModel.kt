package com.app.appagence.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.appagence.app.model.Product
import com.app.appagence.app.usecase.GetProductUseCase
import com.app.appagence.utils.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getProductUseCase: GetProductUseCase
): ViewModel() {

    val productListLive = MutableLiveData<Event<List<Product>>>()

    fun getProductList() {
        viewModelScope.launch {
            productListLive.postValue(Event(getProductUseCase()))
        }
    }

}