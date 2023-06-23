package com.business.fityou.data.database.dao

import androidx.room.*
import com.business.fityou.data.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product WHERE id =:id")
    fun get(id : Int): Flow<ProductEntity?>

    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product : ProductEntity)

    @Delete
    suspend fun remove(product: ProductEntity)
}