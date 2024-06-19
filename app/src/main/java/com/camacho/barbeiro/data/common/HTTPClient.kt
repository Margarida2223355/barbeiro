package com.camacho.barbeiro.data.common

import com.camacho.barbeiro.MyApplication
import com.camacho.barbeiro.utils.json.JSONParser
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class HTTPClient<T>(service: Class<T>) {
    companion object {
        private const val HEADER_PARAMETER_AUTHORIZATION = "Authorization"
        private const val HEADER_PARAMETER_LANG = "Accept-Language"
        private const val HEADER_PARAMETER_ACCEPT = "Accept"
        private const val HEADER_PARAMETER_CLIENT = "Client"
        private const val CALL_TIME_OUT = 10

        var token: String? = null
    }

    var client: T?

    init {
        val httpClientBuilder = OkHttpClient.Builder()
        val defaultInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
            request.addHeader(HEADER_PARAMETER_ACCEPT, "application/json")
            request.addHeader(HEADER_PARAMETER_LANG, Locale.getDefault().toString())
            request.addHeader(HEADER_PARAMETER_CLIENT, "mobile")
            chain.proceed(request.build())
        }

        httpClientBuilder.addInterceptor(defaultInterceptor)
        httpClientBuilder.connectTimeout(CALL_TIME_OUT.toLong(), TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(CALL_TIME_OUT.toLong(), TimeUnit.SECONDS)

        val httpClient = httpClientBuilder.build()
        val gson = JSONParser.getGson()

        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(MyApplication.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        client = retrofit.create(service)
    }

    fun get(): T {
        return client!!
    }
}