package com.app.appagence.app.usecase

import com.app.appagence.app.model.User
import com.app.appagence.ui.login.LoginRepository
import javax.inject.Inject

class SetUserUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke(user: User) =
        repository.setUserData(user)
}