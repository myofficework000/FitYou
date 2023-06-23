package com.business.fityou.ui.composables.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.business.fityou.R

@Composable
fun ProductMacroNutriments(calories: Double?, carbs: Double?, proteins: Double?, fats: Double?) {
    Card(shape = RoundedCornerShape(12.dp), backgroundColor = MaterialTheme.colors.surface) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            MacroNutriment(stringResource(R.string.product_macro_calories), calories, MaterialTheme.colors.onSurface)
            MacroNutriment(stringResource(R.string.product_macro_carbs), carbs, Color.Green)
            MacroNutriment(stringResource(R.string.product_macro_proteins), proteins, Color.Blue)
            MacroNutriment(stringResource(R.string.product_macro_fats), fats, Color.Magenta)
        }
    }

}

@Composable
fun MacroNutriment(label: String, value: Double?, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$value",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = color
        )
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colors.onSurface)
    }
}