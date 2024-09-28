package com.example.contactlistjc.ui.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ChatScreen(
    onGoBackClick: () -> Unit = {}
) {
    Column {
        Text(
            "Chat screen"
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
fun ChatScreenPreview() {
    ChatScreen()
}