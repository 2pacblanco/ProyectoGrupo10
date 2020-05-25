package com.example.proyecto_todolist_grupo10.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.DateFormat

data class Users(@SerializedName("email") var email: String, val pass: String, @SerializedName("name") var name: String, @SerializedName("last_name") var last_name : String, @SerializedName("phone") var phone : String,@SerializedName("profile_photo") var profile_photo : String, var UsersLists: ArrayList<Lists>):
    Serializable {}

data class Lists(var items: ArrayList<Item>, val name: String, val position: Int, var itemsComplete: ArrayList<Item>?): Serializable {}

data class Item(var name: String, var estado: Int, var prioridad: Int, var notas : String , var plazo: String, var check : Int): Serializable {}


public class UserList {
    @SerializedName("data")
    lateinit var list : List<Users>
}