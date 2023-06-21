package com.business.fityou

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.business.fityou.data.service.WorkoutTimerService
import com.business.fityou.ui.navigation.RootNavGraph
import com.business.fityou.ui.theme.DynamicThemes
import com.business.fityou.ui.theme.FitYouDynamicTheme
import com.business.fityou.ui.theme.InFitTheme
import com.business.fityou.util.getTimeStringFromDouble
import com.business.fityou.viewmodel.SettingsViewModel
import com.business.fityou.viewmodel.UserViewModel
import com.business.fityou.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController:NavHostController

    private val userViewModel: UserViewModel by viewModels()
    private val workoutViewModel: WorkoutViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private var timeElapsed = 0.0
    private lateinit var serviceIntent: Intent


    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            timeElapsed = intent.getDoubleExtra(WorkoutTimerService.TIME_ELAPSED, 0.0)
            workoutViewModel.timerText = getTimeStringFromDouble(timeElapsed)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        serviceIntent = Intent(this, WorkoutTimerService::class.java)
        registerReceiver(
            updateTime,
            IntentFilter(WorkoutTimerService.TIMER_UPDATED)
        )
        setContent {
            FitYouDynamicTheme( themeMode = decideTheme(settingsViewModel.currentTheme) ) {
                navController = rememberNavController()
                RootNavGraph(
                    navController,
                    userViewModel,
                    workoutViewModel,
                    settingsViewModel
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(serviceIntent)
    }

    @Composable
    private fun decideTheme(theme: DynamicThemes?) = theme ?: (
        if (isSystemInDarkTheme()) DynamicThemes.DEFAULT_DARK
        else DynamicThemes.DEFAULT_LIGHT
    )
}


