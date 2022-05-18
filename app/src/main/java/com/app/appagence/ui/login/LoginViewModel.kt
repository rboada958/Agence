package com.app.appagence.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.appagence.app.model.Product
import com.app.appagence.app.model.User
import com.app.appagence.app.usecase.GetUserUseCase
import com.app.appagence.app.usecase.SetUserUseCase
import com.app.appagence.utils.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val setUserUseCase: SetUserUseCase,
    val getUserUseCase: GetUserUseCase
) : ViewModel() {

    val userDataLive = MutableLiveData<Event<User>>()

    fun setUser(user: User) {
        viewModelScope.launch {
            setUserUseCase(user)
        }
    }

    fun getUserData() {
        viewModelScope.launch{
            userDataLive.postValue(Event(getUserUseCase()))
        }
    }
}