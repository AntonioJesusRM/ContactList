package com.example.contactlistjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.contactlistjc.ui.theme.ContactListJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactListJCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ContactListApp()
                }
            }
        }
    }
}

@Composable
fun ContactListApp() {
    val navController = rememberNavController()

    MainNavHost(navController = navController)
}