package com.app.appagence.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.appagence.app.model.User
import com.app.appagence.app.usecase.GetProfileDataUseCase
import com.app.appagence.utils.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getProfileDataUseCase: GetProfileDataUseCase
) : ViewModel() {

    val profileDataLive = MutableLiveData<Event<User>>()

    fun getProfileData() {
        viewModelScope.launch {
            profileDataLive.postValue(Event(getProfileDataUseCase()))
        }
    }
}