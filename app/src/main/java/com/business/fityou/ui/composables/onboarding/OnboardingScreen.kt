package com.business.fityou.ui.composables.onboarding

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.business.fityou.R
import com.business.fityou.ui.navigation.Screens
import com.business.fityou.ui.theme.BackgroundCreamWhite

@Composable
fun OnboardingScreen(navController: NavController) {
    val context = LocalContext.current
    var currentPage by remember { mutableStateOf(0) }

    val pageTitles = listOf(
        R.string.onboarding_title_one,
        R.string.onboarding_title_two,
        R.string.onboarding_title_three,
    )

    val pageDescriptions = listOf(
        R.string.onboarding_description_one,
        R.string.onboarding_description_two,
        R.string.onboarding_description_three,
    )

    val pageAnimations = listOf(
        R.raw.animation_1,
        R.raw.animation_2,
        R.raw.animation_3,
    )

    BackHandler(onBack = {
        if (currentPage > 0) {
            currentPage--
        } else {
            (context as Activity).finish()
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedIcon(
            animationRes = pageAnimations[currentPage],
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(id = pageTitles[currentPage]),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(id = pageDescriptions[currentPage]),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        DotsIndicator(
            pageCount = 3,
            currentPage = currentPage,
            onPageSelected = { page -> currentPage = page },
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            onClick = {
                if (currentPage < 2) currentPage++
                else navController.navigate(Screens.Login.route) {
                    popUpTo(Screens.Onboarding.route) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text(
                text = if (currentPage < 2) stringResource(id = R.string.button_next) else stringResource(
                    id = R.string.button_lets_go
                ),
                fontSize = 16.sp,
                color = BackgroundCreamWhite
            )
        }
    }
}

@Composable
fun DotsIndicator(
    pageCount: Int,
    currentPage: Int,
    onPageSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (page in 0 until pageCount) {
            val isSelected = page == currentPage
            val color = if (isSelected) Green else Green.copy(alpha = 0.3f)
            val width by animateDpAsState(
                targetValue = if (isSelected) 32.dp else 8.dp,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .clickable { onPageSelected(page) }
                    .background(color = color, shape = CircleShape)
            )
        }
    }
}

@Composable
fun AnimatedIcon(
    animationRes: Int,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(animationRes)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
}