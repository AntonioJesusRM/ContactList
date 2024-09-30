package com.example.contactlistjc.ui.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactlistjc.data.repository.local.UserDB
import com.example.contactlistjc.data.repository.response.BaseResponse
import com.example.contactlistjc.domain.usecase.AddUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddUserUiState())
    val uiState: StateFlow<AddUserUiState> = _uiState.asStateFlow()

    fun quitError(user: UserDB) {
        _uiState.value = _uiState.value.copy(user = user, error = null)
    }

    fun saveUser(
        user: UserDB, onSaveClick: () -> Unit = {}
    ) {
        val isValid = validateUser(user)
        if (isValid) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true)
                addUserUseCase(user).collect {
                    when (it) {
                        is BaseResponse.Error -> {
                            _uiState.value = _uiState.value.copy(
                                user = user,
                                nameError = true,
                                isLoading = false,
                                error = it.error.message
                            )
                        }

                        is BaseResponse.Success -> {
                            _uiState.value = _uiState.value.copy(
                                user = UserDB("", "", "", "", ""),
                                isLoading = false,
                            )
                            onSaveClick()
                        }
                    }
                }
            }
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