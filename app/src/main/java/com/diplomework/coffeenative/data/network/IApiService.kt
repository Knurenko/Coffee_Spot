package com.diplomework.coffeenative.data.network

import com.diplomework.coffeenative.data.model.Product
import com.diplomework.coffeenative.data.model.Transaction
import retrofit2.http.*

interface IApiService {

    @GET("/api/item")
    suspend fun fetchProducts(): List<Product>

    @GET("/api/transaction")
    suspend fun fetchTransactions(): List<Transaction>

    @POST("/api/transaction")
    suspend fun sendTransaction(@Body transaction: Transaction)

    @DELETE("/api/transaction")
    suspend fun removeTransaction(@Query("id") id: Int)
}