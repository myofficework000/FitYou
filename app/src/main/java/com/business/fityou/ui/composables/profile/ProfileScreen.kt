package com.business.fityou.ui.composables.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.business.fityou.ui.composables.RegularButton
import com.business.fityou.ui.composables.home.Title
import com.business.fityou.ui.theme.darkBlue
import kotlin.reflect.KFunction0

@Composable
fun ProfileScreen(
    onSignOut: KFunction0<Unit>
) {

    Surface(modifier = Modifier.fillMaxSize(), color = darkBlue) {

        Title(text = "coming soon", modifier = Modifier.fillMaxSize())

        Box(Modifier.fillMaxSize()) {
            RegularButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = "Sign out (placeholder)"
            ) { onSignOut() }
        }

    }

}