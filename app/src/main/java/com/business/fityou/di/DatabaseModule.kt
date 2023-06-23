package com.business.fityou.di

import android.content.Context
import androidx.room.Room
import com.business.fityou.data.database.FoodiezDatabase
import com.business.fityou.data.database.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Object that groups the singletons of database.
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    /**
     * Creation of the Database instance.
     * In DEBUG version the database is not encrypted, otherwise we use android-database-sqlcipher
     * @see <a href="https://github.com/sqlcipher/android-database-sqlcipher">android-database-sqlcipher</a>
     */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FoodiezDatabase {
        return Room.databaseBuilder(
            context,
            FoodiezDatabase::class.java,
            FoodiezDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideProductDao(database: FoodiezDatabase): ProductDao = database.productDao()
}