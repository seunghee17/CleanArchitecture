package com.example.cleanarchitecture.domain.repository

import com.example.cleanarchitecture.data.model.Response
import com.example.cleanarchitecture.data.network.ApiResult

interface Repository {
    suspend fun getInfo(): ApiResult<Response>
}