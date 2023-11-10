package com.business.fityou.ui.composables.meal


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CircleNotifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.business.fityou.domain.product.MealType
import com.business.fityou.ui.composables.common.MealsList
import com.business.fityou.R
import com.business.fityou.ui.navigation.Screens

@Composable
fun HomeScreen(navController: NavController, viewModel: MealViewModel = hiltViewModel()) {
    val products by viewModel.products.collectAsState(initial = emptyList())
    val stats by  viewModel.stats.collectAsState()

    var fabState by remember { mutableStateOf(MultiFabState.COLLAPSED) }

    Scaffold(
        backgroundColor = colors.background,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            HomeFloatingActionButton(navController) {
                fabState = it
            }
        }
    ) {
        val alpha = if (fabState == MultiFabState.EXPANDED) 0.3f else 1f

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .alpha(animateFloatAsState(alpha).value)
        ) {
            item { HomeHeader(navController) }
            item { Spacer(modifier = Modifier.size(25.dp)) }
            item { CaloriesProgress(stats) }
            item { Spacer(modifier = Modifier.size(25.dp)) }
            item { MacroNutrimentsCard(stats) }
            item { Spacer(modifier = Modifier.size(25.dp)) }
            item { MealsList(navController, MealType.BREAKFAST, products, viewModel::removeProduct) }
            item { MealsList(navController, MealType.LUNCH, products, viewModel::removeProduct) }
            item { MealsList(navController, MealType.SNACK, products, viewModel::removeProduct) }
            item { MealsList(navController, MealType.DINER, products, viewModel::removeProduct) }
        }
    }
}

@Composable
fun HomeFloatingActionButton(navController: NavController, onStateChanged: (MultiFabState) -> Unit ) {
    MultiFloatingActionButton(
        fabIcon = Icons.Filled.Add,
        items = listOf(
            FabItem(Icons.Filled.QrCodeScanner, stringResource(R.string.home_fab_scan_product)) {
                navController.navigate(Screens.Scan.route)
            },
            FabItem(Icons.Default.Search, stringResource(R.string.home_fab_search_product)) {
                navController.navigate(Screens.Search.route)
            }
        ),
        showLabels = false,
        onStateChanged = { onStateChanged(it) }
    )
}

@Composable
fun HomeHeader(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                //TODO : set user's picture
                painter = rememberImagePainter("https://images.pexels.com/photos/2531553/pexels-photo-2531553.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"),
                contentDescription = stringResource(R.string.home_header_user_photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, colors.onSurface, CircleShape)
                    .clickable {
                        navController.navigate(Screen.Settings.route)
                    }
            )
            Column(verticalArrangement = Arrangement.spacedBy((-4).dp, Alignment.CenterVertically)) {
                //TODO : set user's name
                Text(text = stringResource(R.string.home_header_welcome), fontSize = 14.sp, color = colors.onBackground)
                Text(text = "Jonathan Doe", fontSize = 18.sp, fontWeight = ExtraBold, color = colors.onBackground)
            }
        }
        Icon(
            imageVector = Icons.Outlined.CircleNotifications,
            contentDescription = stringResource(R.string.home_header_notifications),
            modifier = Modifier
                .size(34.dp)
                .clickable {}
        )
    }
}

@Composable
fun CaloriesProgress(stats: Statistic) {
    //TODO : use preferences for max calories
    val left = 2500 - stats.totalCalories

    val progress: Float by animateFloatAsState(
        targetValue = stats.totalCalories.toFloat() / 2500f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    Column(verticalArrangement = Arrangement.Center) {
        LinearProgressIndicator(
            progress = progress,
            color = colors.primary,
            backgroundColor = colors.surface,
            modifier = Modifier
                .fillMaxWidth()
                .size(20.dp)
                .graphicsLayer {
                    shape = RoundedCornerShape(8.dp)
                    clip = true
                }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.home_stats_calories_total, stats.totalCalories), fontSize = 13.sp, fontWeight = Bold, color = colors.onBackground)
            Text(text = stringResource(R.string.home_stats_calories_left, left), fontSize = 13.sp, fontWeight = Bold, color = colors.onBackground)
        }
    }
}

