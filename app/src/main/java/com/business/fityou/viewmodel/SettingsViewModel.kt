package com.business.fityou.viewmodel

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.business.fityou.domain.SettingsRepository
import com.business.fityou.ui.theme.DynamicThemes
import com.business.fityou.ui.theme.toDynamicTheme
import com.business.fityou.ui.theme.toName
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: SettingsRepository
): ViewModel() {
    var currentTheme: DynamicThemes? by mutableStateOf(DynamicThemes.DEFAULT_LIGHT)
        private set

    init{
        currentTheme = repo.getString("theme").takeIf { it.isNotBlank() }?.toDynamicTheme()
    }

    fun setTheme(
        themeMode: DynamicThemes?
    ) {
        currentTheme = themeMode
        repo.setString("theme", themeMode?.toName() ?: "")
    }
}