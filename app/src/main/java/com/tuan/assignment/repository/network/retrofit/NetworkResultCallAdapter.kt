package com.tuan.assignment.repository.network.retrofit

import com.tuan.assignment.repository.network.ApiResult
import com.tuan.assignment.repository.network.QueryError
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class NetworkResultCallAdapter<T : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, QueryError>
) : CallAdapter<T, Call<ApiResult<T>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ApiResult<T>> {
        return NetworkResultCall(call, errorBodyConverter)
    }
}