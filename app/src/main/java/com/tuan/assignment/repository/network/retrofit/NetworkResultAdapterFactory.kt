package com.tuan.assignment.repository.network.retrofit

import com.tuan.assignment.repository.network.ApiResult
import com.tuan.assignment.repository.network.QueryError
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResultAdapterFactory private constructor() : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        val callType = getParameterUpperBound(0, returnType as ParameterizedType) as ParameterizedType
        if (getRawType(callType) != ApiResult::class.java) {
            return null
        }

        val successBodyType = getParameterUpperBound(0, callType)

        val errorConverter =
            retrofit.nextResponseBodyConverter<QueryError>(null, QueryError::class.java, annotations)

        return NetworkResultCallAdapter<Any>(successBodyType, errorConverter)
    }

    companion object {
        fun create(): NetworkResultAdapterFactory = NetworkResultAdapterFactory()
    }
}