package com.app.appagence.ui.profile

import com.app.appagence.app.model.User

interface ProfileRepository {

    fun getProfileData() : User
}