package com.business.fityou.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.business.fityou.ui.composables.exercises.ExerciseDetailScreen
import com.business.fityou.ui.composables.exercises.ExercisesScreen
import com.business.fityou.ui.composables.home.HomeScreen
import com.business.fityou.ui.composables.product.ProductScreen
import com.business.fityou.ui.composables.profile.ProfileScreen
import com.business.fityou.ui.composables.search.SearchScreen
import com.business.fityou.ui.composables.settings.SettingsScreen
import com.business.fityou.ui.composables.stats.StatsDetailScreen
import com.business.fityou.ui.composables.stats.StatsScreen
import com.business.fityou.ui.composables.workout.WorkoutDetailScreen
import com.business.fityou.ui.composables.workout.WorkoutPlanSetUpScreen
import com.business.fityou.ui.composables.workout.WorkoutScreen
import com.business.fityou.ui.theme.veryDarkBlue
import com.business.fityou.viewmodel.SettingsViewModel
import com.business.fityou.viewmodel.UserViewModel
import com.business.fityou.viewmodel.WorkoutViewModel

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    userViewModel: UserViewModel,
    workoutViewModel: WorkoutViewModel,
    settingsViewModel: SettingsViewModel,
    scaffoldState: ScaffoldState
) {

    navigation(startDestination = Screens.Home.route, route = MAIN_ROUTE)
    {
        composable(
            route = Screens.Home.route
        ) {
            HomeScreen(navController, userViewModel, workoutViewModel, scaffoldState)
            bottomBarState.value = true
        }

        composable(
            route = Screens.Stats.route
        ) {
            StatsScreen(navController, workoutViewModel)
            bottomBarState.value = true
        }

        composable(
            route = Screens.Profile.route
        ) {
            /*
            TODO: PROFILE SCREEN
             */
            ProfileScreen(userViewModel::logOut)
            bottomBarState.value = true
        }

        composable(
            route = Screens.StatsDetails.route
        ) {
            StatsDetailScreen(navController, workoutViewModel)
            bottomBarState.value = true
        }

        composable(
            route = Screens.WorkoutDetails.route
        ) {
            WorkoutDetailScreen(navController, workoutViewModel)
            bottomBarState.value = true
        }

        composable(
            route = Screens.Workout.route
        ) {
            WorkoutScreen(workoutViewModel, navController)
            bottomBarState.value = true
        }

        composable(route = Screens.Exercises.route) {
            ExercisesScreen(navController, workoutViewModel)
            bottomBarState.value = true
        }

        composable(route = Screens.ExerciseDetails.route) {
            ExerciseDetailScreen(Modifier, navController, workoutViewModel)
            bottomBarState.value = true
        }

        composable(route = Screens.WorkoutPlanSetUp.route) {
            WorkoutPlanSetUpScreen(workoutViewModel = workoutViewModel, navController)
            bottomBarState.value = true
        }

        composable(route = Screens.Search.route) {
            SearchScreen(navController = navController)
            bottomBarState.value = true
        }

        composable(
            route = Screens.Product.route,
            arguments = Screens.Product.navArg
        ) {
            ProductScreen(navController = navController)
        }

        composable(route = Screens.Settings.route) {
            SettingsScreen(
                settingsViewModel = settingsViewModel,
                onSignOut = userViewModel::logOut
            )
            bottomBarState.value = true
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController = rememberNavController(),
    bottomBarState: MutableState<Boolean>
) {
    val screens = listOf(
        Screens.Home,
        Screens.Exercises,
        Screens.Search,
        Screens.Profile,
        Screens.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AnimatedVisibility(
        visible = bottomBarState.value, enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation(
            modifier = Modifier
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                }
                .height(70.dp),
            elevation = 10.dp,
            backgroundColor = MaterialTheme.colors.surface,
        ) {

            screens.forEach {

                this@BottomNavigation.AddItem(
                    screens = it,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }


        }
    }

}

@Composable
fun RowScope.AddItem(
    screens: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    BottomNavigationItem(
        label = {
            Text(text = stringResource(id = screens.title))
        },

        onClick = {
            navController.navigate(screens.route) {
                popUpTo(Screens.Home.route)
                launchSingleTop = true
            }
        },
        icon = { Icon(imageVector = screens.icon!!, contentDescription = null) },
        selected = currentDestination?.hierarchy?.any { it.route == screens.route } == true,
        selectedContentColor = veryDarkBlue,
        unselectedContentColor = veryDarkBlue.copy(ContentAlpha.disabled),


        )
}