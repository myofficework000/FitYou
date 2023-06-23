package com.business.fityou.ui.composables.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.business.fityou.R
import com.business.fityou.ui.composables.RadioButtons
import com.business.fityou.ui.composables.RegularButton
import com.business.fityou.ui.composables.home.SubHeading
import com.business.fityou.ui.composables.home.Title
import com.business.fityou.ui.theme.DynamicThemes
import com.business.fityou.ui.theme.darkBlue
import com.business.fityou.ui.theme.holoGreen
import com.business.fityou.ui.theme.toDynamicTheme
import com.business.fityou.ui.theme.toName
import com.business.fityou.viewmodel.SettingsViewModel
import kotlin.reflect.KFunction0

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onSignOut: KFunction0<Unit>
) {
    var itemThemeOpened by remember{ mutableStateOf(false) }
    var itemLogoutOpened by remember{ mutableStateOf(false) }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        ItemTheme(currentTheme = settingsViewModel.currentTheme) { itemThemeOpened = true }
        ItemLogout{ itemLogoutOpened = true }
    }

    if (itemThemeOpened) ThemeSelectionDialog(
        currentTheme = settingsViewModel.currentTheme,
        onDismiss = { itemThemeOpened = false },
        onSelect = { settingsViewModel.setTheme(it) }
    )

    if (itemLogoutOpened) LogoutConfirmDialog(
        onDismiss = { itemLogoutOpened = false },
        onConfirm = { onSignOut() }
    )
}

@Composable
private fun SettingsItem(
    label: String,
    onClick: () -> Unit
) {
    TextButton(onClick) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                color = MaterialTheme.colors.onBackground
            )
            Icon(Icons.Filled.ArrowForward, "", tint = MaterialTheme.colors.onBackground)
        }
    }
}

@Composable
private fun ItemTheme(
    currentTheme: DynamicThemes?,
    onOpen: () -> Unit
) {
    SettingsItem(
        label = "Theme: ${currentTheme?.toName() ?: "System Default"}",
        onClick = onOpen
    )
}

@Composable
private fun ItemLogout(
    openDialog: () -> Unit
) {
    SettingsItem(label = "Log out", onClick = openDialog)
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

@Composable
private fun LogoutConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
            color = darkBlue,
            elevation = 20.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                SubHeading(text = stringResource(R.string.logout), color = holoGreen)
                Title(stringResource(R.string.logout_alert_text))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement
                        .spacedBy(20.dp, Alignment.CenterHorizontally)
                ) {
                    RegularButton(text = stringResource(R.string.confirm), onClick = onConfirm)
                    RegularButton(text = stringResource(R.string.dismiss), onClick = onDismiss)
                }
            }
        }
    }
}