package com.example.proyecto_todolist_grupo10.networking

import com.example.proyecto_todolist_grupo10.configuration.api_key
import com.example.proyecto_todolist_grupo10.model.*
import okhttp3.ResponseBody
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

    @Headers("token: $api_key")
    @DELETE("/lists/{id}")
    fun deleteGist(@Path("id") id: String?): Call<ResponseBody>

    @Headers("token: $api_key")
    @GET("/items")
    fun getItemsbyList(@Query("list_id") list_id : String): Call<ArrayList<Item>>

    @Headers("token: $api_key ")
    @POST("/items")
    fun addItems( @Body items: Items): Call<ArrayList<Item>>

    @Headers("token: $api_key ")
    @PUT("/items/{id}")
    fun updateItem( @Body item: aux_item2, @Path("id") id : String): Call<Item>

    @Headers("token: $api_key")
    @DELETE("/items/{id}")
    fun deleteItem(@Path("id") id: String?): Call<ResponseBody>

    @Headers("token: $api_key")
    @POST("/shared_lists/share")
    fun shareaList(@Body sharedlist : share_a_list1) : Call<share_a_list>

    @Headers("token: $api_key")
    @GET("/shared_lists")
    fun getSharedLists() : Call<ArrayList<aux_shared_lists>>

    @Headers("token: $api_key")
    @GET("/lists/{id}")
    fun getListbyId(@Path("id") list_id : String) : Call<Lists>

}