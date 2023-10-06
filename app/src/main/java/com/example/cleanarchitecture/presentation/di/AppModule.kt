package com.example.cleanarchitecture.presentation.di

import com.example.cleanarchitecture.data.network.ApiResultCallAdapterFactory
import com.example.cleanarchitecture.data.network.ApiService
import com.example.cleanarchitecture.BuildConfig
import com.example.cleanarchitecture.domain.repository.Repository
import com.example.cleanarchitecture.data.repositoryimpl.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Singleton
    @Binds
    //repository가 필요할때마다 repositoryImpl인스턴스 제공
    abstract fun bindRepository(
        repositoryImpl : RepositoryImpl
    ): Repository
}
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASEURL)
            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }
}