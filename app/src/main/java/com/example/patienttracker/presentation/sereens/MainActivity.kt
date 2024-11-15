package com.example.patienttracker.presentation.sereens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.patienttracker.presentation.navigation.NavGraph
import com.example.patienttracker.presentation.viewModel.PatientDetailsViewModel
import com.example.patienttracker.theme.PatientTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PatientTrackerTheme {
                val navController = rememberNavController()

                // Setup Scaffold
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    // Pass NavController to NavGraph
                    NavGraph(navController = navController)
                }
            }
        }
    }
}

