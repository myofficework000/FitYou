package com.business.fityou.data.network

import com.business.fityou.data.network.api.ProductApi
import com.business.fityou.data.network.api.SearchApi
import com.business.fityou.data.network.dto.product.ProductDto
import com.business.fityou.data.network.dto.search.ProductListDto
import javax.inject.Inject

internal class RemoteDatasourceImpl @Inject constructor(
    private val productApi: ProductApi,
    private val searchApi: SearchApi,
) : RemoteDatasource {

    override suspend fun getProduct(code: String): DataResult<ProductDto, Throwable> {
        runCatching {
            val response = productApi.getProduct(code)
            response.takeIf { it.isSuccessful }?.body()?.let {
                return DataResult.Success(it)
            } ?: throw Exception("An error occurred: ${response.errorBody()?.string()}")
        }.getOrElse {
            return DataResult.Error(it)
        }

    }

    override suspend fun searchByQuery(query: String): DataResult<ProductListDto, Throwable> {
        runCatching {
            val response = searchApi.searchByQuery(query)
            response.takeIf { it.isSuccessful }?.body()?.let {
                return DataResult.Success(it)
            } ?: throw Exception("An error occurred: ${response.errorBody()?.string()}")
        }.getOrElse {
            return DataResult.Error(it)
        }

    }

}