package com.business.fityou.domain

interface SettingsRepository {
    fun getString(name: String): String
    fun setString(name: String, value: String)
}