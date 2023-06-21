package com.business.fityou.ui.composables.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.business.fityou.ui.composables.RadioButtons
import com.business.fityou.ui.theme.DynamicThemes
import com.business.fityou.ui.theme.toDynamicTheme
import com.business.fityou.ui.theme.toName
import com.business.fityou.viewmodel.SettingsViewModel
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

@Composable
fun SettingsScreen(
    vm: SettingsViewModel,
    onSignOut: KFunction0<Unit>
) {
    var itemThemeOpened by remember{ mutableStateOf(false) }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        ItemTheme(currentTheme = vm.currentTheme) { itemThemeOpened = true }
    }

    if (itemThemeOpened) ThemeSelectionDialog(
        currentTheme = vm.currentTheme,
        onDismiss = { itemThemeOpened = false },
        onSelect = { vm.setTheme(it) }
    )
}

@Composable
private fun ItemTheme(
    currentTheme: DynamicThemes?,
    onOpen: () -> Unit
) {
    TextButton(
        onOpen
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Theme: ${currentTheme?.toName() ?: "System Default"}",
                color = MaterialTheme.colors.onBackground
            )
            Icon(Icons.Filled.ArrowForward, "", tint = MaterialTheme.colors.onBackground)
        }
    }
}

@Composable
private fun ThemeSelectionDialog(
    currentTheme: DynamicThemes?,
    onDismiss: () -> Unit,
    onSelect: (DynamicThemes?) -> Unit // This doesn't include the selected one
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(onClick = onDismiss)
    ) {
        RadioButtons(
            listOf("System Default").plus(DynamicThemes.values().map { it.toName() }),
            currentTheme?.toName() ?: "System Default",
            Modifier.align(Alignment.Center)
        ) {
            with(it.toDynamicTheme()) {
                if (this != currentTheme) {
                    onSelect(this)
                    onDismiss()
                }
            }
        }
    }
}