package com.business.fityou.domain.product

data class Product(
    val id: Int? = null,
    val remoteId: String,
    val name: String,
    val brand: String,
    val imageURL: String,
    var type: MealType? = null,
    var quantity: Int = 100,
    val fatsPer100: Double,
    val carbsPer100: Double,
    val proteinsPer100: Double,
    val caloriePer100: Double,
) {
    fun fatsByQuantity() = fatsPer100 * quantity / 100
    fun carbsByQuantity() = carbsPer100 * quantity / 100
    fun proteinsByQuantity() = proteinsPer100 * quantity / 100
    fun caloriesByQuantity() = caloriePer100 * quantity / 100
}
