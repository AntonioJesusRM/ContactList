package com.example.contactlistjc.domain.usecase

import com.example.contactlistjc.data.repository.DataProvider
import com.example.contactlistjc.data.repository.local.UserDB
import com.example.contactlistjc.data.repository.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddUserUseCase @Inject constructor(private val dataProvider: DataProvider) {
    operator fun invoke(user: UserDB): Flow<BaseResponse<Boolean>> {
        return dataProvider.addUser(user)
    }
}