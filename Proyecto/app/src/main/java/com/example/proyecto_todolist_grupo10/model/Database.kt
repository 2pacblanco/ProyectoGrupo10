package com.example.proyecto_todolist_grupo10.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Users_table::class, Lists_table::class, Items_table::class],version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class Database: RoomDatabase(){
    abstract fun Users_tableDao(): Users_tableDao
    abstract fun Lists_tableDao(): Lists_tableDao
    abstract fun Items_tableDao(): Items_tableDao
}