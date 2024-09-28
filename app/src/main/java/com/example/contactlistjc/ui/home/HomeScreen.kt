package com.example.contactlistjc.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    onChatClick: () -> Unit = {}, onAddUserClick: () -> Unit = {}, onOptionClick: () -> Unit = {}
) {
    Column {
        Text(
            "Home screen"
        )
        Button(onClick = onChatClick) {
            Text(
                "GO CHAT"
            )
        }
        Button(onClick = onAddUserClick) {
            Text(
                "GO ADD USER"
            )
        }
        Button(onClick = onOptionClick) {
            Text(
                "GO OPTION"
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}