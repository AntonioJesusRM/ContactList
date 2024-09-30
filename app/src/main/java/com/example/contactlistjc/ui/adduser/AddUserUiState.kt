package com.example.contactlistjc.ui.adduser

import com.example.contactlistjc.data.repository.local.UserDB

data class AddUserUiState(
    val user: UserDB = UserDB(
        avatar = "",
        name = "",
        lastName = "",
        address = "",
        phoneNumber = ""
    ),
    val nameError: Boolean = false,
    val lastNameError: Boolean = false,
    val addressError: Boolean = false,
    val phoneNumberError: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
