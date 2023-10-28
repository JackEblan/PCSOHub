package com.android.pcsohub.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.pcsohub.common.Screen
import com.android.pcsohub.common.all_screens
import com.android.pcsohub.presentation.about.AboutScreen
import com.android.pcsohub.presentation.lotto_list.LottoListScreen
import com.android.pcsohub.presentation.lucky_pick.LuckyPickScreen
import com.android.pcsohub.ui.theme.PCSOHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            PCSOHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val navBackStackEntry by navController.currentBackStackEntryAsState()

                    val selectedItem = when (navBackStackEntry?.destination?.route) {
                        Screen.LottoList.route -> 0
                        Screen.LuckyPick.route -> 1
                        Screen.About.route -> 2
                        else -> 0
                    }

                    Scaffold(bottomBar = {
                        NavigationBar {
                            all_screens.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    icon = {
                                        when (index) {
                                            0 -> Icon(
                                                Icons.Filled.Home, item.label
                                            )

                                            1 -> Icon(
                                                Icons.Filled.Star, item.label
                                            )

                                            2 -> Icon(
                                                Icons.Filled.AccountBox, item.label
                                            )
                                        }
                                    },
                                    label = { Text(text = item.label) },
                                    selected = selectedItem == index,
                                    onClick = {
                                        navController.navigate(
                                            route = all_screens[index].route,
                                            navOptions = NavOptions.Builder().setPopUpTo(
                                                route = navController.graph.findStartDestination().route,
                                                inclusive = false,
                                                saveState = true
                                            ).setLaunchSingleTop(true).setRestoreState(true).build()
                                        )
                                    },
                                    alwaysShowLabel = false
                                )
                            }
                        }
                    }) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.LottoList.route,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            composable(Screen.LottoList.route) {
                                LottoListScreen()
                            }

                            composable(Screen.LuckyPick.route) {
                                LuckyPickScreen()
                            }

                            composable(Screen.About.route) {
                                AboutScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}