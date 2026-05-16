package com.ziad.appcomposewithktor.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ziad.appcomposewithktor.ui.detail.UserDetailScreen
import com.ziad.appcomposewithktor.ui.profile.ProfileScreen
import com.ziad.appcomposewithktor.ui.userlist.UserListScreen
import com.ziad.appcomposewithktor.ui.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(viewModel: UserViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val users by viewModel.users.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.UserList.route,
        ) {
            composable(Screen.UserList.route) {
                UserListScreen(
                    viewModel = viewModel,
                    onUserClick = { userId ->
                        navController.navigate("user_detail/$userId")
                    }
                )
            }
            composable(Screen.UserDetail.route) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")?.toInt()
                val user = users.find { it.id == userId }
                user?.let {
                    UserDetailScreen(
                        user = it,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }

}