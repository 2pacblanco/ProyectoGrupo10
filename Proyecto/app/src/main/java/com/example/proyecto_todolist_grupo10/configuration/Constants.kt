package com.example.proyecto_todolist_grupo10.configuration

import com.example.proyecto_todolist_grupo10.model.Lists
import com.example.proyecto_todolist_grupo10.networking.HerokuApi
import com.example.proyecto_todolist_grupo10.networking.HerokuApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val BASE_URL: String = "https://6bc67247-ecef-494d-990f-65b783067900.mock.pstmn.io/"

//url api entrega 3
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
}