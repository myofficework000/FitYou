package com.business.fityou.ui.composables.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.business.fityou.ui.composables.common.MealCard
import com.business.fityou.ui.composables.product.ProductSource
import com.business.fityou.ui.navigation.Screens

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    val searchUiState = viewModel.searchUiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .background(colors.background)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        SearchBar(navController = navController, onValueChanged = { viewModel.searchRequested(it) })

        if (searchUiState.value.isLoading)
            SearchLoading()
        else
            SearchResult(navController = navController, searchUiState = searchUiState)
    }
}


@Composable
fun SearchResult(navController: NavController, searchUiState: State<SearchUiState>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxHeight()
    ) {
        searchUiState.value.productList.forEach { product ->
            item {
                MealCard(
                    product = product,
                    onClick = {
                        navController.navigate(
                            Screens.Product.navigationLink(
                                ProductSource.REMOTE,
                                product.remoteId.toLong()
                            )
                        )
                    })
            }
        }
    }
}

@Composable
fun SearchLoading() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = colors.primary,
            strokeWidth = 10.dp,
            modifier = Modifier.size(100.dp)
        )
    }
}

