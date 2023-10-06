package com.example.cleanarchitecture.data.network
import com.example.cleanarchitecture.data.model.Response
import retrofit2.http.GET
interface ApiService {
    @GET("15077586/v1/centers?serviceKey=3iIG2f6bToise1WSrcOcNRv3AFxaD%2Byrak%2ByQki%2B7WocvjQVIRL2FS3AuYbiLho%2FIe%2FBbMo6VnbrvLRZvdgB5g%3D%3D")
    suspend fun getInfo(): ApiResult<Response>
}