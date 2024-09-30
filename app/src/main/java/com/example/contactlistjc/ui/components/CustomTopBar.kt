package com.example.contactlistjc.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.contactlistjc.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    backButton: Boolean,
    optionButton: Boolean,
    onOptionClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
        )
    }, navigationIcon = {
        if (backButton) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.tool_bar_back_content_description)
                )
            }
        }
    }, actions = {
        if (optionButton) {
            IconButton(onClick = onOptionClick) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.tool_bar_options_content_description)
                )
            }
        }
    })
}

@Preview
@Composable
fun CustomTopBarPreview() {
    CustomTopBar(title = "HOME", backButton = true, optionButton = false)
}