package com.business.fityou.ui.composables.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.business.fityou.R
import com.business.fityou.domain.product.MealType
import com.business.fityou.ui.navigation.Screens

@Composable
fun ProductScreen(navController: NavController, viewModel: ProductViewModel = hiltViewModel()) {
    val state = viewModel.state
    val focusManager = LocalFocusManager.current

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            SaveProductButton {
                viewModel.saveProduct(state.product)
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.Home.route) {
                        inclusive = true
                    }
                }
            }
        },
        backgroundColor = colors.background,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(it)
                .padding(bottom = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            ProductHeader(
                url = state.product?.imageURL,
                name = state.product?.name,
                brand = state.product?.brand
            )
            ProductMacroNutriments(
                state.product?.caloriesByQuantity(),
                state.product?.carbsByQuantity(),
                state.product?.proteinsByQuantity(),
                state.product?.fatsByQuantity()
            )
            ProductQuantity(state)
        }
    }

}

@Composable
fun ProductHeader(url: String?, name: String?, brand: String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberImagePainter(url),
            contentDescription = stringResource(R.string.product_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(180.dp)
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Text(
            text = name ?: stringResource(R.string.data_not_available),
            fontSize = 24.sp,
            fontWeight = ExtraBold,
            textAlign = Center,
            color = colors.onBackground
        )
        Text(text = brand ?: stringResource(R.string.data_not_available), fontSize = 12.sp, color = colors.onBackground)
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductQuantity(state: ProductState) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        val focusManager = LocalFocusManager.current
        var quantity by remember { mutableStateOf(0) }
        var expanded by remember { mutableStateOf(false) }
        val options = MealType.values().map { it.tag }

        state.product?.type ?: run { state.product?.type = MealType.getMealTypeByCurrentHour() }
        quantity = state.product?.quantity ?: 0

        OutlinedTextField(
            shape= RoundedCornerShape(12.dp),
            value = quantity.toString(),
            onValueChange = {
                quantity = if (it.isBlank()) 0 else it.toInt()
                state.product?.quantity = quantity
            },
            label = { Text(stringResource(R.string.product_quantity_label)) },
            singleLine = true,
            trailingIcon = { Text(stringResource(R.string.product_quantity_unit_label)) },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colors.onSurface,
                unfocusedBorderColor = colors.onSurface
            ),
            modifier = Modifier.weight(1f)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                shape= RoundedCornerShape(12.dp),
                readOnly = true,
                value = state.product?.type?.tag ?: MealType.getMealTypeByCurrentHour().tag,
                onValueChange = {},
                label = { Text(stringResource(R.string.product_meal_type_label)) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colors.onSurface,
                    unfocusedBorderColor = colors.onSurface
                ),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selected ->
                    DropdownMenuItem(
                        onClick = {
                            focusManager.clearFocus()
                            state.product?.type = MealType.valueOf(selected.uppercase())
                            expanded = false
                        }
                    ) {
                        Text(text = selected)
                    }
                }
            }
        }
    }
}

@Composable
fun SaveProductButton(onClick: (() -> Unit)) {
    FloatingActionButton(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colors.primary,
        onClick = { onClick() },
        modifier = Modifier.size(60.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = stringResource(R.string.product_save),
            tint = Color.White,
            modifier = Modifier.size(35.dp)
        )
    }
}