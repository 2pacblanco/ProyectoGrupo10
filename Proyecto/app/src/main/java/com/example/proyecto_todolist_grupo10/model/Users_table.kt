package com.example.proyecto_todolist_grupo10.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.proyecto_todolist_grupo10.model.Users_table.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName

@Dao
interface Users_tableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Users_table)

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getAllUsers(): List<Users_table>

}

@Entity(tableName = TABLE_NAME)
data class Users_table(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: String,
    @ColumnInfo(name = URL_PHOTO)
    val url_photo: String,
    val email: String,
    val pass: String,
    val name: String,
    val last_name: String,
    val phone : String
)
{
    companion object {
        const val TABLE_NAME = "Users_table"
        const val ID = "id"
        const val URL_PHOTO = "url_photo"

    }
}