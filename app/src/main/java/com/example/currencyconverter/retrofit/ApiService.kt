package com.example.currencyconverter.retrofit

import com.example.currencyconverter.models.Currency
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("json")
    fun getCurrencyData(): Call<List<Currency>>
}