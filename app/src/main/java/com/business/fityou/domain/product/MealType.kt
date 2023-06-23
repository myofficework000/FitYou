package com.business.fityou.domain.product

import java.time.ZonedDateTime

enum class MealType(val tag: String) {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    SNACK("Snack"),
    DINER("Diner");


    companion object {
        fun getMealTypeByCurrentHour(hour: Int = ZonedDateTime.now().hour): MealType {
            return when (hour) {
                in 6..11 -> BREAKFAST
                in 12..16 -> LUNCH
                in 20..23 -> DINER
                else -> SNACK
            }
        }
    }
}