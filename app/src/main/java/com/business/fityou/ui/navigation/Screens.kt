package com.business.fityou.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.SetMeal
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.business.fityou.R
import com.business.fityou.ui.composables.product.ProductSource

const val ROOT_ROUTE = "root_route"
const val LOGIN_ROUTE = "login_route"
const val MAIN_ROUTE = "main_route"

sealed class Screens(
    val route: String,
    @StringRes
    val title: Int, val icon: ImageVector? = null
) {

    object Onboarding : Screens(route = "onboarding", title = R.string.app_name)

    object Home : Screens(route = "home_screen", title = R.string.home, icon = Icons.Rounded.Home)
    object Login : Screens(route = "login_screen", title = R.string.login)
    object Signup : Screens(route = "signup_screen", title = R.string.signup)
    object Workout : Screens(route = "workout_screen", title = R.string.workout)
    object Stats :
        Screens(route = "stats_screen", title = R.string.stats, icon = Icons.Rounded.Analytics)

    object WorkoutDetails :
        Screens(route = "workout_details_screen", title = R.string.workout_details)

    object StatsDetails : Screens(route = "stats_details_screen", title = R.string.stats_details)
    object Profile :
        Screens(route = "profile_screen", title = R.string.profile, icon = Icons.Rounded.Person)

    object Exercises : Screens(
        route = "exercises_screen",
        title = R.string.exercises,
        icon = Icons.Rounded.FitnessCenter
    )

    object ExerciseDetails : Screens(route = "exercises_details_screen", title = R.string.exercise)
    object WorkoutPlanSetUp :
        Screens(route = "workout_plan_setup_screen", title = R.string.set_up_workout_plan_heading)

    object Settings :
        Screens(route = "settings", title = R.string.settings, icon = Icons.Rounded.Settings)


    object Search : Screens(route = "search", title = R.string.Search, icon = Icons.Rounded.SetMeal)
    object Product : Screens(
        route = "product/{$ARG_PRODUCT_SOURCE}/{$ARG_PRODUCT_ID}",
        title = R.string.product
    ) {
        fun navigationLink(source: ProductSource, id: Long): String =
            "product/$source/$id"

        val navArg = listOf(
            navArgument(ARG_PRODUCT_SOURCE) { type = NavType.StringType },
            navArgument(ARG_PRODUCT_ID) { type = NavType.LongType }
        )
    }
}

const val ARG_PRODUCT_ID = "arg_product_id"
const val ARG_PRODUCT_SOURCE = "arg_product_source"