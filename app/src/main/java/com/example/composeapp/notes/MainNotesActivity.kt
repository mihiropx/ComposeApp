package com.example.composeapp.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.notes.navigation.SetupNavGraph
import com.example.composeapp.notes.ui.theme.ComposeAppTheme

class MainNotesActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {

                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
