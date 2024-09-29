package com.example.contactlistjc.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.contactlistjc.ui.components.CustomTopBar

@Composable
fun HomeScreen(
    onChatClick: () -> Unit = {}, onAddUserClick: () -> Unit = {}, onOptionClick: () -> Unit = {}
) {
    Scaffold(topBar = {
        CustomTopBar(
            "Home screen",
            backButton = false,
            optionButton = true,
            onOptionClick
        )
    }, content = { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
        }
    })
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}