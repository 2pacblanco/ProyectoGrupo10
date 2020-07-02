package com.example.proyecto_todolist_grupo10.networking

import com.example.proyecto_todolist_grupo10.configuration.api_key
import com.example.proyecto_todolist_grupo10.model.Lists
import com.example.proyecto_todolist_grupo10.model.ListsList
import com.example.proyecto_todolist_grupo10.model.Users
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.*

interface HerokuApi {

    @GET("/users/get_self")
    fun getUser(@Header("token") token: String?): Call<Users>
    //falta el update que va en la view de edición de profile


    @GET("/lists")
    fun getLists(@Header("token")token: String?): Call<ArrayList<Lists>>

    @Headers("token: $api_key")
    @POST("/lists")
    fun addList(@Body listData: Lists): Call<Lists>

}