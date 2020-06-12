package com.example.proyecto_todolist_grupo10

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import kotlinx.android.synthetic.main.activity_create_list.*
import java.time.LocalDate

class AddItem : AppCompatActivity() {

    companion object{
        const val  ITEM = "ITEM"
        var newItem : Item? = null
        fun newInstance1(context: Context) = Intent(context, AddItem::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        newItem = null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        supportActionBar?.hide()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createItem(view: View) {
        val nameitem :String = findViewById<EditText>(R.id.NameItem).text.toString()
        val noteitem : String = findViewById<EditText>(R.id.NoteItem).text.toString()
        val checkPriority : CheckBox = findViewById<CheckBox>(R.id.checkBoxPrioridad)

        if(nameitem == ""){
            Toast.makeText(this.applicationContext, "Ingrese nombre, es obligatorio para crear un item", Toast.LENGTH_SHORT).show()
        }
        else{
            if( checkPriority.isChecked && noteitem != ""){
                    newItem = Item(nameitem,0, 1,noteitem, LocalDate.now().plusDays(30) ,LocalDate.now(), 0)
            }
            if(!checkPriority.isChecked && noteitem != ""){
                newItem = Item(nameitem,0, 0,noteitem,LocalDate.now().plusDays(30) ,LocalDate.now(),0)
            }
            if(checkPriority.isChecked && noteitem == ""){
                newItem = Item(nameitem,0, 1,"", LocalDate.now().plusDays(30),LocalDate.now(),0)
            }
            if(!checkPriority.isChecked && noteitem == ""){
                newItem = Item(nameitem,0, 0,"", LocalDate.now().plusDays(30),LocalDate.now(),0)
            }

            val data = Intent().apply {
                putExtra(ITEM, newItem!!)
            }
            setResult(Activity.RESULT_OK,data)
            finish()
        }

    }

    fun cancel(view: View){
        finish()
    }
}
