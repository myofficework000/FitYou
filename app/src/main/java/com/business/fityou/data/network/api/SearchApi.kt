package com.business.fityou.data.network.api

import com.business.fityou.data.network.dto.search.ProductListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * OpenFoodFacts search api interface.
 */
internal interface SearchApi {

    @GET("search.pl")
    suspend fun searchByQuery(
        @Query("search_terms") query: String,
        @Query("page") page: String = "1",
        @Query("json") format: String = "1"
    ): Response<ProductListDto>
}