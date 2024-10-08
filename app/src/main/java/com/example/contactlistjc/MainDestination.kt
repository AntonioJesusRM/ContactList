package com.example.contactlistjc

sealed interface MainDestination {
    val route: String
}

data object Home : MainDestination {
    override val route = "home"
}

data object AddOrEditUser : MainDestination {
    override val route = "addOrEditUser/{userId}"
    fun createRoute(userId: String?) = if (userId != null) {
        "addOrEditUser/$userId"
    } else {
        "addOrEditUser/"
    }
}

data object Chat : MainDestination {
    override val route = "chat"
}

data object Options : MainDestination {
    override val route = "option"
}