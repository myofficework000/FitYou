package com.business.fityou.ui.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.business.fityou.ui.composables.login.LoginScreen
import com.business.fityou.ui.composables.signup.SignUpScreen
import com.business.fityou.viewmodel.UserViewModel

fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    userViewModel: UserViewModel,
    scaffoldState: ScaffoldState
) {

    navigation(startDestination = Screens.Login.route, route = LOGIN_ROUTE)
    {
        composable(route = Screens.Login.route){
            LoginScreen(navController,userViewModel,scaffoldState)
            bottomBarState.value = false
        }
        composable(route = Screens.Signup.route){
            SignUpScreen(navController,userViewModel,scaffoldState)
            bottomBarState.value = false
        }
    }
}