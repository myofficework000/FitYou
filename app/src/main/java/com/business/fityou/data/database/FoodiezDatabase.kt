package com.business.fityou.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.business.fityou.data.database.dao.ProductDao
import com.business.fityou.data.database.entity.ProductEntity

/**
 * Class that defines the database room.
 */
@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)

abstract class FoodiezDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        /**
         * Name of the database.
         */
        const val DATABASE_NAME: String = "database"

        /**
         * Key of the database passphrase.
         */
        const val PASSPHRASE_KEY = "DataBaseModulePassphrase"

        /**
         * Length of the database passphrase.
         */
        const val PASSPHRASE_LENGTH = 256
    }
}
