package com.tuan.assignment.repository.network.retrofit

import com.tuan.assignment.repository.network.ApiResult
import com.tuan.assignment.repository.network.QueryError
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

class NetworkResultCall<T : Any>(
    private val delegate: Call<T>,
    private val errorConverter: Converter<ResponseBody, QueryError>
) : Call<ApiResult<T>> {

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = handleApi(response)
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = ApiResult.Exception(t)
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }
        })
    }

    fun <T : Any> handleApi(response: Response<T>): ApiResult<T> {
        return try {
            val body = response.body()
            if (response.isSuccessful) {
                ApiResult.Success(body)
            } else {
                ApiResult.Error(
                    code = response.code(),
                    data = errorConverter.convert(response.errorBody())
                )
            }
        } catch (e: Throwable) {
            ApiResult.Exception(e)
        }
    }

    override fun execute(): Response<ApiResult<T>> = throw NotImplementedError()
    override fun clone(): Call<ApiResult<T>> = NetworkResultCall(delegate.clone(), errorConverter)
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun cancel() { delegate.cancel() }
}