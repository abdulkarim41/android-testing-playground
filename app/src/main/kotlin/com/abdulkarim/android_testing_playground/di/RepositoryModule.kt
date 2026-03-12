package com.abdulkarim.android_testing_playground.di

import com.abdulkarim.android_testing_playground.data.repoimpl.ProductRepositoryImpl
import com.abdulkarim.android_testing_playground.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindProductRepository(productRepoImpl: ProductRepositoryImpl): ProductRepository
}