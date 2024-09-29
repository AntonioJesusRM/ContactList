package com.example.contactlistjc.ui.adduser

import androidx.lifecycle.ViewModel
import com.example.contactlistjc.data.repository.local.UserDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(AddUserUiState())
    val uiState: StateFlow<AddUserUiState> = _uiState.asStateFlow()

    fun saveUser(
        user: UserDB, onSaveClick: () -> Unit = {}
    ) {
        val isValid = validateUser(user)
        if (isValid) {
            onSaveClick()
        }
    }

    private fun validateUser(user: UserDB): Boolean {
        val nameError = user.name.isEmpty()
        val lastNameError = user.lastName.isEmpty()
        val addressError = user.address.isEmpty()
        val phoneNumberError = user.phoneNumber.isEmpty()

        _uiState.value = _uiState.value.copy(
            nameError = nameError,
            lastNameError = lastNameError,
            addressError = addressError,
            phoneNumberError = phoneNumberError
        )

        return !nameError && !lastNameError && !addressError && !phoneNumberError
    }

}