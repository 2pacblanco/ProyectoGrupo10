package com.example.proyecto_todolist_grupo10

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import kotlinx.android.synthetic.main.activity_create_list.*

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

    fun createList(view: View) {
        var items = mutableListOf<Item>()

        items.add(Item("item creado automáticamente 1"))
        items.add(Item("item creado automáticamente 2"))
        items.add(Item("item creado automáticamente 3"))
        items.add(Item("item creado automáticamente 4"))

        val list = Listas(items,inputNameOfList.text.toString(),position = 0)
        val data = Intent().apply {
            putExtra(LIST,list)
        }
        setResult(Activity.RESULT_OK,data)
        finish()
    }
}
