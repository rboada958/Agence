package com.app.appagence.ui.login

import com.app.appagence.app.model.User

interface LoginRepository {

    suspend fun setUserData(user: User)
    fun getUserData() : User
}