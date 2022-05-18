package com.app.appagence.app.di

import com.app.appagence.app.data.LocalDataStore
import com.app.appagence.ui.home.HomeRepository
import com.app.appagence.ui.home.HomeRepositoryImpl
import com.app.appagence.ui.login.LoginRepository
import com.app.appagence.ui.login.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    fun providesLoginRepository(localDataStore: LocalDataStore): LoginRepository =
        LoginRepositoryImpl(localDataStore)

    @Provides
    fun providesHomeRepository(localDataStore: LocalDataStore): HomeRepository =
        HomeRepositoryImpl(localDataStore)

}