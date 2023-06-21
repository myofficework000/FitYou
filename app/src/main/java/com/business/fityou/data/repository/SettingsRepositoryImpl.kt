package com.business.fityou.data.repository

import android.content.SharedPreferences
import com.business.fityou.domain.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val pref: SharedPreferences
): SettingsRepository {
    private val edit = pref.edit()

    override fun getString(name: String): String = pref.getString(name, "") ?: ""

    override fun setString(name: String, value: String) {
        edit.putString(name, value).apply()
    }
}