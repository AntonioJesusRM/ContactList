package com.example.contactlistjc.data.repository.local

import android.content.Context
import com.example.contactlistjc.R
import com.example.contactlistjc.data.repository.DataSource
import com.example.contactlistjc.data.repository.response.BaseResponse
import com.example.contactlistjc.domain.model.ErrorModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val appDatabaseManager: AppDatabaseManager,
    @ApplicationContext private val context: Context
) : DataSource {

    override fun getUsers(): Flow<BaseResponse<List<UserDB>>> = flow {
        try {
            appDatabaseManager.db.userDAO().getUsers().collect { users ->
                emit(BaseResponse.Success(users))
            }
        } catch (e: Exception) {
            val errorModel =
                ErrorModel(
                    "",
                    "",
                    e.message ?: context.getString(R.string.database_error_unknown_error)
                )
            emit(BaseResponse.Error(errorModel))
        }
    }

}