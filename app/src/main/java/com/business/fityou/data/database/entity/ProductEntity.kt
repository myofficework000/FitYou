package com.business.fityou.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.business.fityou.domain.product.MealType
import com.business.fityou.domain.product.Product


@Entity(tableName = "product")
data class ProductEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "brand") val brand: String,
    @ColumnInfo(name = "type") val type: MealType?,
    @ColumnInfo(name = "imageURL") val imageURL: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "fatsPer100") val fatsPer100: Double,
    @ColumnInfo(name = "carbsPer100") val carbsPer100: Double,
    @ColumnInfo(name = "proteinsPer100") val proteinsPer100: Double,
    @ColumnInfo(name = "caloriePer100") val caloriePer100: Double,
    @ColumnInfo(name = "remoteId") val remoteId: String,

    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun toProduct() =
        Product(
            id = id,
            name = name,
            brand = brand,
            type = type,
            imageURL = imageURL,
            quantity = quantity,
            fatsPer100 = fatsPer100,
            carbsPer100 = carbsPer100,
            proteinsPer100 = proteinsPer100,
            caloriePer100 = caloriePer100,
            remoteId = remoteId,
        )
}




