package com.example.contactlistjc.data.repository

import com.example.contactlistjc.data.repository.local.UserDB
import com.example.contactlistjc.data.repository.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getUsers(): Flow<BaseResponse<List<UserDB>>>
}