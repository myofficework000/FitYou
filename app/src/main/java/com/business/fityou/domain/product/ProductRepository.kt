package com.business.fityou.domain.product

import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun saveProduct(product: Product)

    suspend fun removeProduct(product: Product)

    suspend fun getProduct(id: Int): Flow<Product?>

    suspend fun getAllProducts(): Flow<List<Product>>

    suspend fun getRemoteProduct(code: String): Flow<Resource<Product>>

    suspend fun searchByQuery(query: String): Flow<Resource<List<Product>>>
}