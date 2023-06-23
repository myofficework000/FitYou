package com.business.fityou.data.network.dto.search

import com.business.fityou.data.network.dto.common.ProductData
import com.google.gson.annotations.SerializedName

data class ProductListDto(
    @SerializedName("count")
    val count: Int,

    @SerializedName("page")
    val page: Int,

    @SerializedName("page_count")
    val pageCount: Int,

    @SerializedName("page_size")
    val pageSize: Int,

    @SerializedName("products")
    val products: List<ProductData>,
)