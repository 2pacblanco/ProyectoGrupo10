package com.example.proyecto_todolist_grupo10.configuration

import com.example.proyecto_todolist_grupo10.ToDoActivity
import com.example.proyecto_todolist_grupo10.model.*
import com.example.proyecto_todolist_grupo10.networking.HerokuApi
import com.example.proyecto_todolist_grupo10.networking.HerokuApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val BASE_URL: String = "https://6bc67247-ecef-494d-990f-65b783067900.mock.pstmn.io/"

//url api entrega 3
//"tx7f53HolxSAuTxtmkTgoemAhhkjAY8gVxM"
//"KoYgX4KLfa8hlM2hwM8p8zRMugdD8E21A5k"
const val api_key = "tx7f53HolxSAuTxtmkTgoemAhhkjAY8gVxM"
const val BASE_URL_API : String = "https://salty-journey-65951.herokuapp.com/"

class RestApiService {
    fun addLists(userData: Lists, onResult: (Lists?) -> Unit){
        val retrofit = HerokuApiService.buildService(HerokuApi::class.java)
        retrofit.addList(userData).enqueue(
            object : Callback<Lists> {
                override fun onFailure(call: Call<Lists>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Lists>, response: Response<Lists>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }

    fun addItems(userItem: Items, onResult: (ArrayList<Item>?) -> Unit){
        val retrofit = HerokuApiService.buildService(HerokuApi::class.java)
        retrofit.addItems(userItem).enqueue(
            object : Callback<ArrayList<Item>> {
                override fun onFailure(call: Call<ArrayList<Item>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<ArrayList<Item>>, response: Response<ArrayList<Item>>) {
                    val addedUser = response.body()
                    println("si respondeee")
                    println(response.body().toString())
                    onResult(addedUser)
                }
            }
        )
    }

    fun shareaList(shareList: share_a_list1 , onResult: (share_a_list?) -> Unit){
        val retrofit = HerokuApiService.buildService(HerokuApi::class.java)
        retrofit.shareaList(shareList).enqueue(
            object : Callback<share_a_list> {
                override fun onFailure(call: Call<share_a_list>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<share_a_list>, response: Response<share_a_list>) {
                    val addedUser = response.body()
                    //println(response.body().toString())
                    onResult(addedUser)
                }
            }
        )
    }

}