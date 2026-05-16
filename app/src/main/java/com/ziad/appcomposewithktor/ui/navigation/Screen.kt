package com.ziad.appcomposewithktor.ui.navigation

sealed class Screen(val route: String) {
    object UserList: Screen("user_list")
    object UserDetail : Screen("user_detail/{userId}")
    object Profile : Screen("profile")
}