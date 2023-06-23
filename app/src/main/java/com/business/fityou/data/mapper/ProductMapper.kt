package com.business.fityou.data.mapper

import com.business.fityou.data.database.entity.ProductEntity
import com.business.fityou.data.network.dto.product.ProductDto
import com.business.fityou.domain.product.Product
import com.business.fityou.data.network.dto.search.ProductListDto


/**
 * Extension of [Product].
 * Maps a [Product] to a [ProductEntity].
 *
 */
fun Product.toEntity(): ProductEntity {
    val entity = ProductEntity(
        name = name,
        type = type,
        brand = brand,
        imageURL = imageURL,
        quantity = quantity,
        fatsPer100 = fatsPer100,
        carbsPer100 = carbsPer100,
        proteinsPer100 = proteinsPer100,
        caloriePer100 = caloriePer100,
        remoteId = remoteId,
    )
    id?.let { entity.id = it }
    return entity
}

/**
 * Extension of [ProductDto].
 * Maps an [ProductDto] to a [Product].
 *
 */
fun ProductDto.toProduct(): Product =
    Product(
        remoteId = data.productID ?: "",
        name = data.productName ?: "",
        brand = data.brands ?: "",
        imageURL = data.imageURL ?: "",
        fatsPer100 = data.nutriments.fat,
        carbsPer100 = data.nutriments.carbohydrates,
        proteinsPer100 = data.nutriments.proteins,
        caloriePer100 = data.nutriments.energyKcal,
    )

/**
 * Extension of [ProductListDto].
 * Maps an [ProductListDto] to a list of [Product].
 *
 */
fun ProductListDto.toProductsList(): List<Product> {
    return products
        .filter { !it.productID.isNullOrBlank() }
        .filter { !it.productName.isNullOrBlank() }
        .filter { !it.imageURL.isNullOrBlank() }
        .map {
            Product(
                name = it.productName ?: "",
                brand = it.brands ?: "",
                imageURL = it.imageURL ?: "",
                fatsPer100 = it.nutriments.fat,
                carbsPer100 = it.nutriments.carbohydrates,
                proteinsPer100 = it.nutriments.proteins,
                caloriePer100 = it.nutriments.energyKcal,
                remoteId = it.productID ?: ""
            )
        }
}