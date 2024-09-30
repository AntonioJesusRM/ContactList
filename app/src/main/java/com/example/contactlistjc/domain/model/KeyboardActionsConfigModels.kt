package com.example.contactlistjc.domain.model

import androidx.compose.ui.focus.FocusRequester

data class KeyboardActionsConfigModels(
    val focusRequester: FocusRequester,
    val nextParam: FocusRequester? = null,
    val onDoneClick: (() -> Unit)? = null
)
