package com.business.fityou.ui.composables.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.business.fityou.ui.composables.home.Title
import com.business.fityou.ui.theme.darkBlue

@Composable
fun ProfileScreen() {

    Surface(modifier = Modifier.fillMaxSize(), color = darkBlue) {

        Title(text = "coming soon", modifier = Modifier.fillMaxSize())

    }

}