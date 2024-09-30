package com.example.contactlistjc.ui.adduser

data class AddUserUiState(
    val nameError: Boolean = false,
    val lastNameError: Boolean = false,
    val addressError: Boolean = false,
    val phoneNumberError: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
