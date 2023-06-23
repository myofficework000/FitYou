package com.business.fityou.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.business.fityou.ui.theme.darkBlue
import com.business.fityou.viewmodel.SettingsViewModel
import com.business.fityou.viewmodel.UserViewModel
import com.business.fityou.viewmodel.WorkoutViewModel

@Composable
fun RootNavGraph(
    navController: NavHostController,
    userViewModel: UserViewModel,
    workoutViewModel: WorkoutViewModel,
    settingsViewModel: SettingsViewModel
) {

    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val scaffoldState = rememberScaffoldState()


    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        LOGIN_ROUTE -> bottomBarState.value = false
        MAIN_ROUTE -> bottomBarState.value = true
    }

    LaunchedEffect(Unit) { userViewModel.checkLoginState() }
    LaunchedEffect(userViewModel.signInState) {
        if (userViewModel.signInState.success) navController.navigate(MAIN_ROUTE) {
            popUpTo(Screens.Home.route) { inclusive = true }
        }
        else navController.navigate(LOGIN_ROUTE) {
            popUpTo(navController.graph.id) { inclusive = true }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            when (bottomBarState.value) {
                true -> BottomNavBar(navController = navController, bottomBarState)
                false -> {}
            }
        },
        backgroundColor = MaterialTheme.colors.background,

        ) {

        NavHost(
            navController = navController,
            startDestination = LOGIN_ROUTE,
            route = ROOT_ROUTE,
            modifier = Modifier.padding(it)
        )
        {

            loginNavGraph(
                navController = navController,
                bottomBarState,
                userViewModel,
                scaffoldState
            )

            mainNavGraph(
                navController = navController,
                bottomBarState,
                userViewModel,
                workoutViewModel,
                settingsViewModel,
                scaffoldState
            )

        }
    }

}