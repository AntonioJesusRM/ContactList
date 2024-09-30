package com.example.contactlistjc.ui.home

import androidx.lifecycle.ViewModel
import com.example.contactlistjc.data.repository.local.UserDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        addUser()
    }

    private fun addUser() {
        val users: MutableList<UserDB> = _uiState.value.listUser.toMutableList()

        users.add(
            UserDB(
                avatar = "",
                name = "Antonio",
                lastName = "Ruiz",
                address = "C/Tristan de Silva",
                phoneNumber = "622940920"
            )
        )
        _uiState.value = _uiState.value.copy(
            listUser = users
        )
    }
}