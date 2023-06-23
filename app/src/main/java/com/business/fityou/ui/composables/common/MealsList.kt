package com.business.fityou.ui.composables.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.business.fityou.domain.product.MealType
import com.business.fityou.domain.product.Product
import com.business.fityou.ui.composables.product.ProductSource
import com.business.fityou.ui.navigation.Screens

@Composable
fun MealsList(navController: NavController, mealType: MealType, products: List<Product>, onSwipeAction : (Product) -> Unit) {
    val filteredProducts = products.filter { it.type == mealType }

    if (filteredProducts.isNotEmpty()) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, bottom = 6.dp)
            ) {
                Text(text = mealType.tag, color = colors.onBackground, fontSize = 18.sp, fontWeight = ExtraBold)
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        ) {
            filteredProducts.forEach {
                SwipeableMealCard(
                    product = it,
                    onSwipe = { onSwipeAction(it) },
                    onClick = {
                        it.id?.let { id ->
                            navController.navigate(
                                Screens.Product.navigationLink(
                                    ProductSource.LOCAL,
                                    id.toLong()
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}