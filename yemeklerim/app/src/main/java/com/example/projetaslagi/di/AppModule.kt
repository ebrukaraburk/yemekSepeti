package com.example.projetaslagi.di

import com.example.projetaslagi.data.datasource.YemeklerDataSource
import com.example.projetaslagi.data.repo.YemeklerRepository
import com.example.projetaslagi.retrofit.ApiUtils
import com.example.projetaslagi.retrofit.YemeklerDao

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideYemeklerRepository(yds: YemeklerDataSource) : YemeklerRepository {
        return YemeklerRepository(yds)
    }

    @Provides
    @Singleton
    fun provideYemeklerDataSource(ydao: YemeklerDao) : YemeklerDataSource {
        return YemeklerDataSource(ydao)
    }

    @Provides
    @Singleton
    fun provideYemeklerDao() : YemeklerDao {
        return ApiUtils.getYemeklerDao()
    }
}