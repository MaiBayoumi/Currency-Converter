package com.example.currencyconverter.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.models.Currency
import com.example.currencyconverter.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModels : ViewModel(){


    private var liveDouble = MutableLiveData<List<Currency>>()

    fun getUsers(): LiveData<List<Currency>> {
        ApiClient.apiService.getCurrencyData().enqueue(object : Callback<List<Currency>> {
            override fun onResponse(call: Call<List<Currency>>, response: Response<List<Currency>>) {
                if (response.isSuccessful){
                    liveDouble.value = response.body()

                }
            }
            override fun onFailure(call: Call<List<Currency>>, t: Throwable) {

            }
        })
        return liveDouble
    }
}