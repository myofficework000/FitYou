package com.business.fityou.ui.composables.product

import com.business.fityou.domain.product.Product

data class ProductState(
    val product : Product? = null,
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
)
