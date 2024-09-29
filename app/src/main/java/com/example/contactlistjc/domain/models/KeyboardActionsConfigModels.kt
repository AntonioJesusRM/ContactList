package com.example.contactlistjc.domain.models

import androidx.compose.ui.focus.FocusRequester

data class KeyboardActionsConfigModels(
    val focusRequester: FocusRequester,
    val nextParam: FocusRequester? = null,
    val onDoneClick: (() -> Unit)? = null
)
