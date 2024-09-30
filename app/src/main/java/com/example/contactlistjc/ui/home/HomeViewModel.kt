package com.example.contactlistjc.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactlistjc.data.repository.response.BaseResponse
import com.example.contactlistjc.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun getUsers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getUsersUseCase().collect {
                when (it) {
                    is BaseResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false, error = it.error.message
                        )
                    }

                    is BaseResponse.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            listUser = it.data
                        )
                    }
                }
            }
        }
    }
}