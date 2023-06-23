package com.business.fityou.ui.composables.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.business.fityou.R

@Composable
fun SearchBar(navController: NavController, onValueChanged: ((String) -> Unit)) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigateUpIcon(navController = navController)
        SearchInputField(onValueChanged = { onValueChanged(it) })
    }
}

@Composable
fun NavigateUpIcon(navController: NavController) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            imageVector = Icons.Default.ArrowBackIos,
            contentDescription = stringResource(R.string.search_bar_icon_back),
            tint = colors.primary
        )
    }
}

@Composable
fun SearchInputField(onValueChanged: ((String) -> Unit)) {
    val focusManager = LocalFocusManager.current
    var state by remember { mutableStateOf(TextFieldValue("")) }


    Surface(
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        BasicTextField(
            value = state,
            onValueChange = {
                state = it
                onValueChanged(it.text)
            },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .background(colors.surface, RoundedCornerShape(8.dp))
                .height(45.dp)
                .fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            decorationBox = { innerTextField ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_bar_icon_search),
                        tint = colors.primary
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (state.text.isEmpty())
                            Text(stringResource(R.string.search_bar_placeholder), color = colors.onSurface)
                        innerTextField()
                    }

                    if (state.text.isNotBlank()) {
                        IconButton(onClick = { state = TextFieldValue("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.search_bar_reset),
                                tint = colors.primary
                            )
                        }
                    }
                }
            }
        )
    }
}