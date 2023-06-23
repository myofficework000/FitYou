package com.business.fityou.di

import com.business.fityou.data.network.api.ProductApi
import com.business.fityou.data.network.api.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Named("product_url")
    fun provideProductEndpoint() = "https://world.openfoodfacts.org/api/v2/"

    @Provides
    @Named("search_url")
    fun provideSearchEndpoint() = "https://world.openfoodfacts.org/cgi/"

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient
            .Builder()
            .build()

    @Singleton
    @Provides
    @Named("product_retrofit")
    fun provideProductRetrofit(client: OkHttpClient, @Named("product_url") url: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .client(client)
            .build()

    @Singleton
    @Provides
    @Named("search_retrofit")
    fun provideSearchRetrofit(client: OkHttpClient, @Named("search_url") url: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideProductApi(@Named("product_retrofit") retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides
    @Singleton
    fun provideSearchApi(@Named("search_retrofit") retrofit: Retrofit): SearchApi =
        retrofit.create(SearchApi::class.java)

}
