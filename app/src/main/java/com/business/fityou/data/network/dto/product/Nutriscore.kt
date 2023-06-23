package com.business.fityou.data.network.dto.product

import com.google.gson.annotations.SerializedName

data class Nutriscore (
    val energy: Long,

    @SerializedName("energy_points")
    val energyPoints: Long,

    val fiber: Long,

    @SerializedName("fiber_points")
    val fiberPoints: Long,

    val grade: String,

    @SerializedName("is_beverage")
    val isBeverage: Long,

    @SerializedName("is_cheese")
    val isCheese: Long,

    @SerializedName("is_fat")
    val isFat: Long,

    @SerializedName("is_water")
    val isWater: Long,

    @SerializedName("negative_points")
    val negativePoints: Long,

    @SerializedName("positive_points")
    val positivePoints: Long,

    val proteins: Double,

    @SerializedName("proteins_points")
    val proteinsPoints: Long,

    @SerializedName("saturated_fat")
    val saturatedFat: Double,

    @SerializedName("saturated_fat_points")
    val saturatedFatPoints: Long,

    @SerializedName("saturated_fat_ratio")
    val saturatedFatRatio: Double,

    @SerializedName("saturated_fat_ratio_points")
    val saturatedFatRatioPoints: Long,

    val score: Long,

    val sodium: Double,

    @SerializedName("sodium_points")
    val sodiumPoints: Long,

    val sugars: Double,

    @SerializedName("sugars_points")
    val sugarsPoints: Long,
)