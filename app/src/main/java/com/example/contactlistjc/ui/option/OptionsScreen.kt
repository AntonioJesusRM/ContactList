package com.example.contactlistjc.ui.option

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OptionsScreen(
    onGoBackClick: () -> Unit = {}
) {
    Column {
        Text(
            "Option screen"
        )
        Button(onClick = onGoBackClick) {
            Text(
                "GO BACK"
            )
        }
    }
}

@Preview
@Composable
fun OptionScreenPreview() {
    OptionsScreen()
}