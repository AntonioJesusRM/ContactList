package com.example.contactlistjc.domain.usecase

import com.example.contactlistjc.data.repository.DataProvider
import com.example.contactlistjc.data.repository.local.UserDB
import com.example.contactlistjc.data.repository.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val dataProvider: DataProvider) {
    operator fun invoke(): Flow<BaseResponse<List<UserDB>>> {
        return dataProvider.getUsers()
    }
}