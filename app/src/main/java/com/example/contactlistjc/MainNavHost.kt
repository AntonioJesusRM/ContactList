package com.example.contactlistjc

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.contactlistjc.ui.adduser.AddUserScreen
import com.example.contactlistjc.ui.chat.ChatScreen
import com.example.contactlistjc.ui.home.HomeScreen
import com.example.contactlistjc.ui.option.OptionsScreen

@Composable
fun MainNavHost(
    navController: NavHostController
) {
    val context = LocalContext.current
    BackHandler {
        if (!navController.popBackStack()) {
            (context as? Activity)?.finish()
        }
    }
    NavHost(
        navController = navController, startDestination = Home.route
    ) {
        composable(route = Home.route) {
            HomeScreen(onChatClick = {
                navController.navigateSingleTopTo(Chat.route)
            }, onAddUserClick = {
                navController.navigateSingleTopTo(AddOrEditUser.createRoute(null))
            }, onEditUserClick = { userId ->
                navController.navigateSingleTopTo(AddOrEditUser.createRoute(userId))
            }, onOptionClick = {
                navController.navigateSingleTopTo(Options.route)
            })
        }

        composable(route = AddOrEditUser.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            AddUserScreen(
                navController,
                userName = userId,
                onSaveClick = {
                    navController.navigateSingleTopTo(Home.route)
                }
            )
        }

        composable(route = Chat.route) {
            ChatScreen(onGoBackClick = {
                navController.navigateSingleTopTo(Home.route)
            })
        }

        composable(route = Options.route) {
            OptionsScreen(onGoBackClick = {
                navController.navigateSingleTopTo(Home.route)
            })
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    popUpTo(
        this@navigateSingleTopTo.graph.findStartDestination().id
    ) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}