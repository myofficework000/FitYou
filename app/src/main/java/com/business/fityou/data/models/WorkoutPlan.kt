package com.business.fityou.data.models

import com.business.fityou.util.DifficultyLevels
import java.time.DayOfWeek

data class WorkoutPlan (
    val name:String? = null,
    val workouts:ArrayList<DayOfWeek>? = null,
    val difficulty: DifficultyLevels.Difficulty?  = null,
    val duration:Int? = null,
)
