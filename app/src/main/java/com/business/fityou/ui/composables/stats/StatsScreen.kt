package com.business.fityou.ui.composables.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.business.fityou.R
import com.business.fityou.data.repository.WorkoutRepositoryImpl
import com.business.fityou.ui.composables.home.Heading
import com.business.fityou.ui.composables.home.SubHeading
import com.business.fityou.ui.navigation.Screens
import com.business.fityou.ui.theme.holoGreen
import com.business.fityou.ui.theme.white
import com.business.fityou.viewmodel.WorkoutViewModel

@Composable
fun StatsScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel
) = with(workoutViewModel) {

    getHistoryData()

    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.stats_screen_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
            modifier = Modifier.padding(start = 15.dp, top = 40.dp, end = 15.dp)
        ) {
            Heading(text = stringResource(R.string.your_stats))
            LazyColumn(
                modifier = Modifier
                    .padding(end = 40.dp, top = 25.dp)
                    .fillMaxHeight()
            ) {
                items(statsExercises.toList()) { exercise ->
                    StatsItem(exercise) {
                        navController.navigate(Screens.StatsDetails.route)
                        workoutViewModel.currentExerciseId = exercise
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}


@Composable
fun StatsItem(exercise: String, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        SubHeading(text = exercise, color = white)
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = holoGreen,
            modifier = Modifier.size(50.dp)
        )
    }
}


@Preview
@Composable
private fun StatsScreenPreview() {
    StatsScreen(rememberNavController(), WorkoutViewModel(WorkoutRepositoryImpl()))
}