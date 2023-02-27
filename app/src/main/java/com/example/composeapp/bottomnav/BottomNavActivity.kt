package com.example.composeapp.bottomnav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.helper.BottomNavItem

class BottomNavActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenView()
        }
    }

    @Preview
    @Composable
    fun MainScreenView() {

        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigation(navController = navController)
            }
        ) {
            NavigationGraph(navController = navController)
        }
    }

    @Composable
    fun BottomNavigation(navController: NavController) {

        val items = listOf(
            BottomNavItem.Explore,
            BottomNavItem.Storage,
            BottomNavItem.Settings
        )

        BottomNavigation {

            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->

                BottomNavigationItem(
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.title)
                        )
                    },
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screenRoute,
                    onClick = {
                        navController.navigate(item.screenRoute) {

                            navController.graph.startDestinationRoute?.let { screen_route ->

                                popUpTo(screen_route) {

                                    saveState = true

                                }

                            }

                            launchSingleTop = true

                            restoreState = true

                        }
                    }
                )

            }

        }
    }

    @Composable
    fun NavigationGraph(
        navController: NavHostController
    ) {

        NavHost(
            navController = navController,

            startDestination = BottomNavItem.Explore.screenRoute,

            builder = {
                composable(BottomNavItem.Explore.screenRoute) {
                    ExploreScreen(this@BottomNavActivity)
                }

                composable(BottomNavItem.Storage.screenRoute) {
                    StorageScreen(this@BottomNavActivity)
                }

                composable(BottomNavItem.Settings.screenRoute) {
                    SettingsScreen(this@BottomNavActivity)
                }
            })

    }
}