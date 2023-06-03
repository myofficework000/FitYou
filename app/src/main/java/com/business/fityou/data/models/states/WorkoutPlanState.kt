package com.business.fityou.data.models.states

import com.business.fityou.data.models.WorkoutPlan

data class WorkoutPlanState(
    val workoutPlan:WorkoutPlan? = null,
    val loading:Boolean = false,
    val error:String? = null
)
