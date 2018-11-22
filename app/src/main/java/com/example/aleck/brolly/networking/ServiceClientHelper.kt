package com.example.aleck.brolly.networking

import com.example.aleck.brolly.model.WeatherResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class ServiceClientHelper  {

    interface RemoteWeatherService {
        @GET("$API_KEY/{latitude},{longitude}?extend=hourly")
        fun requestWeather(
            @Path("latitude") latitude: String,
            @Path("longitude") longitude: String
        ): Observable<WeatherResponse>
    }

    private fun getOkHttpClient () : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }

    fun buildService() : RemoteWeatherService {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
        return retrofit.create(RemoteWeatherService::class.java)
    }
}