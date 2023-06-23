package com.business.fityou.data.network.dto.common

import com.business.fityou.data.network.dto.product.Nutriments
import com.google.gson.annotations.SerializedName

data class ProductData(

    @SerializedName("id")
    val productID: String?,

    @SerializedName("image_url")
    val imageURL: String?,

    @SerializedName("brands")
    val brands: String?,

    val nutriments: Nutriments,

    @SerializedName("nutriscore_grade")
    val nutriscoreGrade: String?,

    @SerializedName("nutriscore_score")
    val nutriscoreScore: Long?,

    @SerializedName("product_name")
    val productName: String?,

    @SerializedName("serving_quantity")
    val servingQuantity: String?,

    @SerializedName("serving_size")
    val servingSize: String?,

//    @SerializedName("additives_n")
//    val additivesN: Long,
//
//    @SerializedName("additives_tags")
//    val additivesTags: List<String>,

//    val complete: Long,
//
//    val completeness: Double,

//    @SerializedName("food_groups")
//    val foodGroups: String,

//    @SerializedName("nutriscore_data")
//    val nutriscore: Nutriscore,

)