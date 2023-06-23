package com.business.fityou.ui.composables.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.business.fityou.R
import com.business.fityou.domain.product.Product
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import de.charlex.compose.RevealValue
import de.charlex.compose.rememberRevealState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableMealCard(product: Product, onClick: () -> Unit, onSwipe: () -> Unit) {
    val revealState = rememberRevealState()
    val scope = rememberCoroutineScope()

    RevealSwipe(
        state = revealState,
        shape = RoundedCornerShape(8.dp),
        directions = setOf(RevealDirection.EndToStart),
        closeOnBackgroundClick = true,
        backgroundCardEndColor = Red,
        hiddenContentEnd = {
            IconButton(onClick = {
                scope.launch {
                    revealState.animateTo(RevealValue.Default)
                }
                onSwipe()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = stringResource(id = R.string.product_delete),
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }
        },
    ) {
        MealCard(product, onClick)
    }
}

@Composable
fun MealCard(product: Product, onClick: (() -> Unit)) {
    Card(
        backgroundColor = colors.surface,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() },
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberImagePainter(product.imageURL),
                contentDescription = stringResource(id = R.string.product_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    maxLines = 1,
                    text = product.name,
                    fontSize = 14.sp,
                    fontWeight = Bold,
                    overflow = Ellipsis,
                    color = colors.onSurface
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "${
                            product.caloriesByQuantity().toInt()
                        } kcal • ${product.quantity} g/ml",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}