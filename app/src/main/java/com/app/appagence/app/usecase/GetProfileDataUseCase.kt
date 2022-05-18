package com.app.appagence.app.usecase

import com.app.appagence.ui.profile.ProfileRepository
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor(private val repository: ProfileRepository) {

    suspend operator fun invoke() =
        repository.getProfileData()
}