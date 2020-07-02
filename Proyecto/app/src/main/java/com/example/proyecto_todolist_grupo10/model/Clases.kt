package com.example.proyecto_todolist_grupo10.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.DateFormat
import java.time.LocalDate

data class Users(@SerializedName("email") var email: String, val pass: String, @SerializedName("first_name") var name: String, @SerializedName("last_name") var last_name : String, @SerializedName("phone") var phone : String,@SerializedName("profile_photo") var profile_photo : String,@SerializedName("api_key") var api_key : String,@SerializedName("created_at") var created_at : String, @SerializedName("updated_at") var updated_at : String,var UsersLists: ArrayList<Lists>):
    Serializable {}

data class Lists(@SerializedName("id") var id : String, @SerializedName("name") val name: String, @SerializedName("position") val position: String, @SerializedName("created_at") val created_at : String, @SerializedName("updated_at") val update_at : String, var itemsComplete: ArrayList<Item>?, var items: ArrayList<Item>): Serializable {}

data class LocationObject( val longitude: Double, val latitude: Double)

data class Item(var name: String, var estado: Int, var prioridad: Int, var notas : String, var plazo: LocalDate, var create_at : LocalDate, var check : Int, var latitude : Double, var longitude : Double): Serializable {}

data class MetaObject( val status: Int)

data class ListsList(val data : ArrayList<Lists>, val meta: MetaObject)

public class UserList {
    @SerializedName("data")
    lateinit var list : List<Users>
}