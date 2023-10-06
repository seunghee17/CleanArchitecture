package com.example.cleanarchitecture.data.network

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

class ApiResultCallAdapter<R> (
    private val successType: Type
) : CallAdapter<R, Call<ApiResult<R>>>{
    override fun adapt(call: Call<R>): Call<ApiResult<R>> = ApiResultCall(call,successType)
    override fun responseType(): Type = successType

    private class ApiResultCall<R>(
        private val delegate: Call<R>,
        private val successType: Type
    ): Call<ApiResult<R>> {
        override fun enqueue(callback: Callback<ApiResult<R>>)= delegate.enqueue(
            object : Callback<R>{
                override fun onResponse(call: Call<R>, response: Response<R>) {
                    callback.onResponse(this@ApiResultCall,Response.success(response.toApiResult()))
                }

                private fun Response<R>.toApiResult(): ApiResult<R> {
                    if(!isSuccessful){
                        val errorBody = errorBody()!!.string()
                        return ApiResult.Failure.HttpError(
                            code = code(),
                            message = message(),
                            body = errorBody
                        )
                    }
                    body()?.let{body-> return ApiResult.successOf(body) }

                    return if(successType == Unit::class.java){
                        @Suppress("UNCHECKED_CAST")
                        (ApiResult.successOf(
                            Unit as R
                        ))
                    } else{
                        ApiResult.Failure.UnknownApiError(
                            IllegalStateException(
                                "Response code is ${code()} but body is null.\n" +
                                        "If you postSomething(): ApiResult<Unit>"
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<R>, throwable: Throwable) {
                    val error = if (throwable is IOException){
                        ApiResult.Failure.NetWorkError(throwable)
                    }else{
                        ApiResult.Failure.UnknownApiError(throwable)
                    }
                    callback.onResponse(this@ApiResultCall, Response.success(error))
                }
            }
        )
        override fun timeout(): Timeout = delegate.timeout()

        override fun isExecuted(): Boolean = delegate.isExecuted

        override fun clone(): Call<ApiResult<R>> = ApiResultCall(delegate.clone(), successType)

        override fun isCanceled(): Boolean = delegate.isCanceled

        override fun cancel() = delegate.cancel()

        override fun execute(): Response<ApiResult<R>> =
            throw UnsupportedOperationException("This adapter does not support sync execution")

        override fun request(): Request = delegate.request()
    }
}
