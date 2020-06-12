package com.example.proyecto_todolist_grupo10

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import kotlinx.android.synthetic.main.activity_create_list.*
import java.time.LocalDate

class CreateList : AppCompatActivity() {

    companion object{
        const val  LIST = "LIST"
        fun newInstance(context: Context) = Intent(context, CreateList::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_list)
        supportActionBar?.hide()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createList(view: View) {
        var items = mutableListOf<Item>()

        var inputname = inputNameOfList.text.toString()

        items.add(Item("item creado automáticamente 1", 0, 0, "Bueno, esta es la nota generada automaticamente al crear la lista",LocalDate.now().plusDays(30), LocalDate.now(),0))
        items.add(Item("item creado automáticamente 2", 0, 0, "Bueno, esta es la nota generada automaticamente al crear la lista",LocalDate.now().plusDays(30), LocalDate.now(),0))
        items.add(Item("item creado automáticamente 3", 0, 0, "Bueno, esta es la nota generada automaticamente al crear la lista", LocalDate.now().plusDays(30),LocalDate.now(),0))
        items.add(Item("item creado automáticamente 4", 0, 0, "Bueno, esta es la nota generada automaticamente al crear la lista",LocalDate.now().plusDays(30), LocalDate.now(),0))


        if (inputname == ""){
            Toast.makeText(this.applicationContext, "Ingrese nombre, es obligatorio para crear una lista", Toast.LENGTH_SHORT).show()
        }
        else{
            val list = Lists(items as ArrayList<Item>,inputname,position = 0,
                itemsComplete = ArrayList<Item>()
            )
            val data = Intent().apply {
                putExtra(LIST,list)
            }
            setResult(Activity.RESULT_OK,data)
            finish()
        }

    }

    fun cancel(view: View){
        finish()
    }
}
