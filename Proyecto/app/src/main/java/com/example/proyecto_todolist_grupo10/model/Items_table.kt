package com.example.proyecto_todolist_grupo10.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import com.example.proyecto_todolist_grupo10.model.Items_table.Companion.ID
import com.example.proyecto_todolist_grupo10.model.Items_table.Companion.TABLE_NAME
import java.time.LocalDate
import java.time.LocalDateTime

@Dao
interface Items_tableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Items_table)

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getAllLists(): List<Items_table>

}

@Entity(tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(entity = Lists_table::class,
        parentColumns = [ID], childColumns = ["list_id"])])
data class Items_table(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: String,
    val list_id: String?,
    val name: String,
    var estado: Int,
    var prioridad: Int,
    var notas : String,
    @TypeConverters(LocalDateTimeConverter::class)
    var plazo: LocalDate,
    @TypeConverters(LocalDateTimeConverter::class)
    var create_at : LocalDate,
    var check : Int
)
{
    companion object {
        const val TABLE_NAME = "Items_table"
        const val ID = "id"
    }
}