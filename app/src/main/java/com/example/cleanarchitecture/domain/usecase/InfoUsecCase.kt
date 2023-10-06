package com.example.cleanarchitecture.domain.usecase

import com.example.cleanarchitecture.data.model.Response
import com.example.cleanarchitecture.data.network.ApiResult
import com.example.cleanarchitecture.domain.repository.Repository
import javax.inject.Inject

class InfoUsecCase @Inject constructor(private val repository:Repository) {
    //invoke usecase를 직접 호출할 수 있게 해주는 연산자 함수
    suspend operator fun invoke(): ApiResult<Response>{
        return repository.getInfo()
    }
}