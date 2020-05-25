package com.example.proyecto_todolist_grupo10.networking

import com.example.proyecto_todolist_grupo10.model.UserList
import com.example.proyecto_todolist_grupo10.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface ApiApi {
    @GET("get_user_info")
    fun getUser(): Call<Users>

    //@GET("gifs/search")
    //fun getGifs(@Header("API_KEY") key: String?, @Query("q") q: String? ): Call<GifList>

    //@POST("votes")
    //@Headers("Content-Type: application/json")
    //fun postVote(@Body body: String,
    //           @Header("Authorization") key: String?
    //): Call<JsonObject>

}