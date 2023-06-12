package com.business.fityou

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.business.fityou.data.service.WorkoutTimerService
import com.business.fityou.ui.navigation.RootNavGraph
import com.business.fityou.ui.theme.InFitTheme
import com.business.fityou.util.getTimeStringFromDouble
import com.business.fityou.viewmodel.UserViewModel
import com.business.fityou.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController:NavHostController

    private val userViewModel: UserViewModel by viewModels()
    private val workoutViewModel: WorkoutViewModel by viewModels()
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
            InFitTheme {
                navController = rememberNavController()
                RootNavGraph(navController,userViewModel,workoutViewModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(serviceIntent)
    }
}


