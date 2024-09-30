package com.example.contactlistjc.data.repository.response

import com.example.contactlistjc.domain.model.ErrorModel

sealed class BaseResponse<T> {
    class Success<T>(val data: T) : BaseResponse<T>()
    class Error<T>(val error: ErrorModel) : BaseResponse<T>()
}