package com.app.appagence.ui.profile

import com.app.appagence.app.data.LocalDataStore
import com.app.appagence.app.model.User
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val localDataStore: LocalDataStore
) : ProfileRepository {
    override fun getProfileData(): User =
        localDataStore.getUserData()
}