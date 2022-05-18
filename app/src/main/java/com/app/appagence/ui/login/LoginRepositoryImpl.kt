package com.app.appagence.ui.login

import com.app.appagence.app.data.LocalDataStore
import com.app.appagence.app.model.User
import com.google.gson.Gson
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val localDataStore: LocalDataStore
) : LoginRepository {

    override suspend fun setUserData(user: User) {
        localDataStore.setUserData(Gson().toJson(user))
    }

    override fun getUserData(): User =
        localDataStore.getUserData()
}