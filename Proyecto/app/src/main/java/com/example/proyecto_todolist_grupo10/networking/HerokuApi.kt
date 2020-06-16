package com.example.proyecto_todolist_grupo10.networking

import com.example.proyecto_todolist_grupo10.model.Users
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HerokuApi {

    @GET("/users/get_self")
    fun getUser(@Header("Authorization") key: String?): Call<Users>

    @GET("/lists")
    fun getLists(@Header("Authorization")key: String?, @Query("loginuser") loginuser: Users?): Call<JsonArray>

}