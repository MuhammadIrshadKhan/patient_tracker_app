package com.example.patienttracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patienttracker.presentation.sereens.AddPatientDetailsScreen
import com.example.patienttracker.presentation.sereens.PatientListScreen
import com.example.patienttracker.utils.Constants
import com.example.patienttracker.utils.Constants.patientId


@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.PatientListScreen.rout
    ) {
        composable(
            route = Screens.PatientListScreen.rout
        ) {
            PatientListScreen(
                onFabClicked = {
                    navController.navigate(Screens.AddPatientDetailsScreen.rout)
                },
                onItemClicked = { patientId ->
                    navController.navigate("${Screens.AddPatientDetailsScreen.rout}/$patientId")
                }
            )
        }

        composable(
            route = "${Screens.AddPatientDetailsScreen.rout}/{${Constants.patientId}}",
            arguments = listOf(navArgument(Constants.patientId) {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getInt(Constants.patientId) ?: -1
            AddPatientDetailsScreen(
                onBackClicked = { navController.navigateUp() },
                onSaveClicked = { navController.navigateUp() },
                patientId = patientId
            )
        }
    }
}