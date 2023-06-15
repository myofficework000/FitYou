package com.business.fityou.ui.composables.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.business.fityou.R
import com.business.fityou.ui.composables.profile.Details.position
import com.business.fityou.ui.theme.darkBlue
import kotlin.reflect.KFunction0


private const val initialImageFloat = 170f

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    onSignOut: KFunction0<Unit>
) {
    val statusBarHeight = /*with(LocalDensity.current) {
        WindowInsets.statusBarsIgnoringVisibility.getBottom(this).toDp()
    }*/ 56.dp

    Surface(modifier = Modifier.fillMaxSize(), color = darkBlue) {
        CollapsibleHeader(
            scrollState = rememberScrollState(),
            profilePic = R.drawable.google_sign_in_logo,
            name = Details.name,
            position = Details.position,
            backgroundModifier = Modifier.background(Color.White),
            heightRange = statusBarHeight to 200.dp
        ) {
            Spacer(
                Modifier
                    .height(2000.dp)
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Color.Yellow, Color.Blue))))
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CollapsibleHeader(
    scrollState: ScrollState,
    profilePic: Int,
    name: String,
    position: String,
    backgroundModifier: Modifier? = null, // Use this only to apply background color/gradient
    // First: Sticky header height
    // Second: Full header height
    heightRange: Pair<Dp, Dp>,
    content: @Composable () -> Unit
) {
    val bgModifier = backgroundModifier ?: Modifier.background(MaterialTheme.colors.background)
    val scrollHeight = with(LocalDensity.current) { scrollState.value.toDp() }
    val headerHeight = remember(scrollHeight) { derivedStateOf {
        if (heightRange.second - heightRange.first <= scrollHeight) heightRange.first
        else heightRange.second - scrollHeight
    } }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth().verticalScroll(scrollState)) {
            Spacer(
                Modifier.fillMaxWidth()
                    .height(heightRange.second)
                    .then(bgModifier)
            )
            content()
        }

        Row(
            Modifier.fillMaxWidth()
                .height(headerHeight.value)
                .then(bgModifier)
        ) {
            Image(
                painter = painterResource(profilePic),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .clip(CircleShape)
            )

            Column(
                Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(name)
                Text(position)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CollapsibleHeaderPreview() {
    val scrollState = rememberScrollState()
    val statusBarHeight = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(this).toDp()
    }

    CollapsibleHeader(
        scrollState = scrollState,
        profilePic = R.drawable.google_sign_in_logo,
        name = "Test name",
        position = "Test position",
        heightRange = Pair(statusBarHeight, 200.dp)
    ) {
        Spacer(
            Modifier
                .height(1000.dp)
                .fillMaxWidth()
                .background(Color.Yellow))
    }
}



private object Details{
    const val name = "Abhishek Pathak"
    const val position = "Android Developer"
    const val email = "myofficework000@gmail.com"
    const val linkedInUrl = "https://www.linkedin.com/in/myofficework/"
    const val githubUrl = "https://github.com"
    const val githubRepoUrl = "https://github.com/myofficework000/Jetpack-Compose-All-In-One-Guide"
    const val twitterUrl = "https://twitter.com/myofficework000"
}