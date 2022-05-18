package com.app.appagence.app.usecase

import com.app.appagence.ui.login.LoginRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: LoginRepository) {

    suspend operator fun invoke() =
        repository.getUserData()
}