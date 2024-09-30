package com.example.contactlistjc.ui.home

import com.example.contactlistjc.data.repository.local.UserDB

data class HomeUiState(
    val listUser: List<UserDB> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)
