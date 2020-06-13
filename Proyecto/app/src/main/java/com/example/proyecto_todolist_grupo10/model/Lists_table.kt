package com.example.proyecto_todolist_grupo10.model

import androidx.room.*
import com.example.proyecto_todolist_grupo10.model.Lists_table.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName

@Dao
interface Lists_tableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Lists_table)

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getAllLists(): List<Lists_table>

}

@Entity(tableName = TABLE_NAME)
data class Lists_table(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: String,
    val name: String,
    val position: String
)
{
    companion object {
        const val TABLE_NAME = "Lists_table"
        const val ID = "id"
    }
}