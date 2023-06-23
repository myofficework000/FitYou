package com.business.fityou.data.network.dto.product

import com.google.gson.annotations.SerializedName

data class Nutriments (

    val carbohydrates: Double,

    val energy: Double,

    @SerializedName("energy-kcal")
    val energyKcal: Double,

    val fat: Double,

    val fiber: Double,

    @SerializedName("nova-group")
    val novaGroup: Long,

    val proteins: Double,

    val salt: Double,

    val sodium: Double,

    val sugars: Double,
)