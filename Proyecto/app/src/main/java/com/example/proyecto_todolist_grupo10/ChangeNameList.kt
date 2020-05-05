package com.example.proyecto_todolist_grupo10

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_change_name_list.*

class ChangeNameList : AppCompatActivity() {

    companion object{
        var listName = "listName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_name_list)
        supportActionBar?.hide()

    }



    fun changeName(view: View){
        var newName = inputNewNameOfList.toString()
        Toast.makeText(view.context, "Se ha cambiado el Nombre de la lista ", Toast.LENGTH_SHORT).show()
        /*
        val intent = Intent(view.context, ToDoActivity::class.java)
        intent.putExtra("nameList", newName)
        view.context.startActivity(intent)
        */
        finish()


    }
}