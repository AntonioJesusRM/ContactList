package com.example.contactlistjc.data.repository

import com.example.contactlistjc.data.repository.local.LocalDataSource
import com.example.contactlistjc.data.repository.local.UserDB
import com.example.contactlistjc.data.repository.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataProvider @Inject constructor(
    private val localDataSource: LocalDataSource
) : DataSource {

    override fun getUsers(): Flow<BaseResponse<List<UserDB>>> {
        return localDataSource.getUsers()
    }

}