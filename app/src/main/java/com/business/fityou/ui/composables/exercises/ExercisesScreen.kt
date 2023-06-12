package com.business.fityou.ui.composables.exercises

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.business.fityou.R
import com.business.fityou.R.*
import com.business.fityou.data.models.Muscle
import com.business.fityou.ui.composables.home.Heading
import com.business.fityou.ui.composables.home.Title
import com.business.fityou.ui.navigation.Screens
import com.business.fityou.ui.theme.darkBlue
import com.business.fityou.ui.theme.lightBlue
import com.business.fityou.viewmodel.WorkoutViewModel

@Composable
fun ExercisesScreen(
    navController: NavHostController = rememberNavController(),
    workoutViewModel: WorkoutViewModel = viewModel()
) {

    Surface(modifier = Modifier.fillMaxSize(), color = darkBlue) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.Start,
        ) {

            Heading(text = stringResource(string.exercises))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ExerciseItem(
                    image = drawable.chest_anatomy_nb,
                    bodyPart = stringResource(R.string.chest)
                ) {
                    workoutViewModel.selectMuscleGroup(listOf(Muscle.Chest), "Chest")
                    navController.navigate(Screens.ExerciseDetails.route)
                }
                ExerciseItem(
                    image = drawable.deltoids_anatomy_nb,
                    bodyPart = stringResource(R.string.shoulders)
                ) {
                    workoutViewModel.selectMuscleGroup(
                        listOf(
                            Muscle.AnteriorDeltoids,
                            Muscle.LateralDeltoids
                        ), "Shoulders"
                    )
                    navController.navigate(Screens.ExerciseDetails.route)
                }
                ExerciseItem(
                    image = drawable.biceps_anatomy_nb,
                    bodyPart = stringResource(R.string.biceps)
                ) {
                    workoutViewModel.selectMuscleGroup(listOf(Muscle.Biceps), "Biceps")
                    navController.navigate(Screens.ExerciseDetails.route)
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ExerciseItem(
                    image = R.drawable.triceps_anatomy_nb,
                    bodyPart = stringResource(R.string.triceps)
                ) {
                    workoutViewModel.selectMuscleGroup(listOf(Muscle.Triceps), "Triceps")
                    navController.navigate(Screens.ExerciseDetails.route)
                }
                ExerciseItem(
                    image = R.drawable.traps_anatomy_nb,
                    bodyPart = stringResource(R.string.upper_back)
                ) {
                    workoutViewModel.selectMuscleGroup(listOf(Muscle.Traps), "Upper Back")
                    navController.navigate(Screens.ExerciseDetails.route)
                }
                ExerciseItem(
                    image = R.drawable.lats_anatomy_nb,
                    bodyPart = stringResource(R.string.lats)
                ) {
                    workoutViewModel.selectMuscleGroup(listOf(Muscle.Lats), "Lats")
                    navController.navigate(Screens.ExerciseDetails.route)
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ExerciseItem(
                    image = R.drawable.calf_muscles_anatomy_nb,
                    bodyPart = stringResource(R.string.calves),
                ) {
                    workoutViewModel.selectMuscleGroup(listOf(Muscle.Calves), "Calves")
                    navController.navigate(Screens.ExerciseDetails.route)
                }
                ExerciseItem(
                    image = R.drawable.hamstrings_anatomy_nb,
                    bodyPart = stringResource(R.string.hamstrings),
                ) {
                    workoutViewModel.selectMuscleGroup(listOf(Muscle.Hamstrings), "Hamstrings")
                    navController.navigate(Screens.ExerciseDetails.route)
                }
                ExerciseItem(
                    image = R.drawable.quadriceps_muscles_anatomy_nb,
                    bodyPart = stringResource(R.string.quads),
                ) {
                    workoutViewModel.selectMuscleGroup(listOf(Muscle.Quads), "Quads")
                    navController.navigate(Screens.ExerciseDetails.route)
                }
            }

        }


    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseItem(
    @DrawableRes
    image: Int,
    bodyPart: String,
    onClick: () -> Unit
) {


    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            shape = CircleShape,
            modifier = Modifier.size(100.dp),
            elevation = 10.dp,
            color = lightBlue,
            onClick = onClick
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Title(text = bodyPart.lowercase())


    }

}

@Preview
@Composable
fun Preview() {

    ExercisesScreen()

}