package com.ziad.appcomposewithktor.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ziad.appcomposewithktor.ui.navigation.AppNavigation
import com.ziad.appcomposewithktor.ui.theme.AppComposeWithKtorTheme
import com.ziad.appcomposewithktor.ui.userlist.UserListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppComposeWithKtorTheme {
                AppNavigation()
            }
        }
    }
}
