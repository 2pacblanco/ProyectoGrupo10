package com.example.proyecto_todolist_grupo10.model

import androidx.viewpager2.widget.ViewPager2
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.DateFormat
import java.time.LocalDate

data class Users(@SerializedName("email") var email: String,
                 val pass: String,
                 @SerializedName("first_name") var name: String,
                 @SerializedName("last_name") var last_name : String,
                 @SerializedName("phone") var phone : String,
                 @SerializedName("profile_photo") var profile_photo : String,
                 @SerializedName("api_key") var api_key : String,
                 @SerializedName("created_at") var created_at : String,
                 @SerializedName("updated_at") var updated_at : String,
                 var UsersLists: ArrayList<Lists>): Serializable {}

data class aux_user(@SerializedName("email") var email : String,
                    @SerializedName("first_name") var first_name : String,
                    @SerializedName("last_name") var last_name : String,
                    @SerializedName("phone") var phone : String,
                    @SerializedName("profile_photo") var profile_photo: String) : Serializable{}



data class Lists(@SerializedName("id") var id : String,
                 @SerializedName("name") val name: String,
                 @SerializedName("position") val position: String,
                 @SerializedName("created_at") val created_at : String,
                 @SerializedName("updated_at") val update_at : String,
                 var itemsComplete: ArrayList<Item>?,
                 var items: ArrayList<Item>, var shared : Boolean): Serializable {}

data class Item(@SerializedName("id") var id : String,
                @SerializedName("name") var name: String,
                @SerializedName("position") val position: String,
                @SerializedName("list_id") var list_id : String,
                @SerializedName("starred") var starred: Boolean,
                @SerializedName("done") var done : Boolean,
                @SerializedName("due_date") var due_date: String,
                @SerializedName("created_at") var create_at : String,
                @SerializedName("notes") var notes : String,
                @SerializedName("updated_at") val updated_at: String,
                var check : Int,
                @SerializedName("lat") var lat : Double,
                @SerializedName("long") var long : Double): Serializable {}

//añadimos clase auxiliar para un correcto posteo de items
data class aux_item(@SerializedName("name") var name: String,
                    @SerializedName("position") var position: String,
                    @SerializedName("list_id") var list_id: String,
                    @SerializedName("starred") var starred : String,
                    @SerializedName("due_date") var due_date: String?,
                    @SerializedName("notes") var notes: String,
                    @SerializedName("lat") val lat: Double,
                    @SerializedName("long") var long : Double) : Serializable{}

data class aux_item2(@SerializedName("name") var name: String,
                    @SerializedName("position") var position: String,
                    @SerializedName("list_id") var list_id: String,
                    @SerializedName("starred") var starred : String,
                    @SerializedName("due_date") var due_date: String?,
                    @SerializedName("notes") var notes: String,
                    @SerializedName("done")var done : String) : Serializable{}

data class Items(@SerializedName("items") var items: ArrayList<aux_item>) : Serializable{}

data class share_a_list1 (@SerializedName("list_id") var list_id : String,
                        @SerializedName("user_email") var user_email : String) : Serializable{}

data class share_a_list(@SerializedName("list_id") var list_id : String,
                        @SerializedName("user_email") var user_email : String,
                        @SerializedName("created_at") var created_at : String,
                        @SerializedName("updated_at") var update_at : String) : Serializable{}

data class aux_shared_lists(@SerializedName("list_id") var list_id : String) : Serializable{}


data class MetaObject( val status: Int)
data class ListsList(val data : ArrayList<Lists>, val meta: MetaObject)
data class LocationObject( val longitude: Double, val latitude: Double)

public class UserList {
    @SerializedName("data")
    lateinit var list : List<Users>
}