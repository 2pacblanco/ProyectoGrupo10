package com.example.proyecto_todolist_grupo10.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.DateFormat
import java.time.LocalDate

data class Users(@SerializedName("email") var email: String, val pass: String, @SerializedName("first_name") var name: String, @SerializedName("last_name") var last_name : String, @SerializedName("phone") var phone : String,@SerializedName("profile_photo") var profile_photo : String,@SerializedName("api_key") var api_key : String,@SerializedName("created_at") var created_at : String, @SerializedName("updated_at") var updated_at : String,var UsersLists: ArrayList<Lists>):
    Serializable {}

data class Lists(var items: ArrayList<Item>, val name: String, val position: Int, var itemsComplete: ArrayList<Item>?): Serializable {}

data class LocationObject( val longitude: Double, val latitude: Double)

data class Item(var name: String, var estado: Int, var prioridad: Int, var notas : String, var plazo: LocalDate, var create_at : LocalDate, var check : Int, var latitude : Double, var longitude : Double): Serializable {}


public class UserList {
    @SerializedName("data")
    lateinit var list : List<Users>
}