package com.business.fityou.data.network.api

import com.business.fityou.data.network.dto.product.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * OpenFoodFacts product api interface.
 */
internal interface ProductApi {

    @GET("product/{code}.json")
    suspend fun getProduct(@Path("code") code: String): Response<ProductDto>
}