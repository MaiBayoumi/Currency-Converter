package com.example.currencyconverter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.currencyconverter.models.Currency

@Dao
interface CurrencyDao {

    @Query("select * from currency")
    fun getAllCurrencyModel(): List<Currency>

    @Insert
    fun addCurrencyModel(currencyListModel: Currency)

    @Insert(onConflict = REPLACE)
    fun addCountries(countryModel: List<Currency?>?)
}