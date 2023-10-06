package com.example.cleanarchitecture.data.repositoryimpl

import com.example.cleanarchitecture.data.model.Response
import com.example.cleanarchitecture.data.network.ApiResult
import com.example.cleanarchitecture.data.network.ApiService
import com.example.cleanarchitecture.domain.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(private val service: ApiService): Repository {
    override suspend fun getInfo(): ApiResult<Response> {
        return service.getInfo()
    }
}