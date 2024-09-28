package com.example.contactlistjc

sealed interface MainDestination {
    val route: String
}

data object Home : MainDestination {
    override val route = "home"
}

data object AddUser : MainDestination {
    override val route = "login"
}

data object Chat : MainDestination {
    override val route = "chat"
}

data object Options : MainDestination {
    override val route = "option"
}