package com.example.proyecto_todolist_grupo10.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Users_table::class, Lists_table::class, Items_table::class],version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun Users_tableDao(): Users_tableDao
    abstract fun Lists_tableDao(): Lists_tableDao
    abstract fun Items_tableDao(): Items_tableDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
    
}