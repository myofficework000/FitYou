package com.business.fityou.ui.composables.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.business.fityou.domain.product.Product
import com.business.fityou.domain.product.ProductRepository
import com.business.fityou.domain.product.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    var searchUiState = MutableStateFlow(SearchUiState())

    private var searchJob : Job? = null

    fun searchRequested(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            searchByQuery(query)
        }
    }

    private fun searchByQuery(query: String) {
        viewModelScope.launch {
            productRepository.searchByQuery(query).collectLatest { result ->
                when (result) {
                    is Resource.Success -> searchUiState.value = searchUiState.value.copy(isLoading = false, productList = result.data ?: emptyList())
                    is Resource.Error -> searchUiState.value = searchUiState.value.copy(isLoading = false, productList = emptyList())
                    is Resource.Loading -> searchUiState.value = searchUiState.value.copy(isLoading = true, productList = emptyList())
                }
            }
        }
    }
}

data class SearchUiState(
    var isLoading : Boolean = false,
    var productList : List<Product> = emptyList()
)