package com.business.fityou.di

import com.business.fityou.data.network.RemoteDatasource
import com.business.fityou.data.network.RemoteDatasourceImpl
import com.business.fityou.data.network.api.ProductApi
import com.business.fityou.data.network.api.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides remote datasource-related instances.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DatasourceModule {

    /**
     * Provides an instance of [RemoteDatasource].
     */
    @Singleton
    @Provides
    fun provideRemoteDataSource(productApi: ProductApi, searchApi: SearchApi): RemoteDatasource =
        RemoteDatasourceImpl(productApi, searchApi)
}