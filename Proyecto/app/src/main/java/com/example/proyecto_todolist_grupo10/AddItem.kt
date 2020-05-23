package com.example.proyecto_todolist_grupo10

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import kotlinx.android.synthetic.main.activity_create_list.*

class AddItem : AppCompatActivity() {

    companion object{
        const val  ITEM = "ITEM"
        var newItem : Item? = null
        fun newInstance1(context: Context) = Intent(context, AddItem::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        supportActionBar?.hide()

    }

    fun createItem(view: View) {
        val nameitem :String = findViewById<EditText>(R.id.NameItem).text.toString()
        val noteitem : String = findViewById<EditText>(R.id.NoteItem).text.toString()
        val checkPriority : CheckBox = findViewById<CheckBox>(R.id.checkBoxPrioridad)

        if(nameitem == ""){
            Toast.makeText(this.applicationContext, "Ingrese nombre, es obligatorio para crear un item", Toast.LENGTH_SHORT).show()
        }
        else{
            if( checkPriority.isChecked && noteitem != ""){
                newItem = Item(nameitem,0, 1,noteitem, "25/12/2020")
            }
            if(!checkPriority.isChecked && noteitem != ""){
                newItem = Item(nameitem,0, 0,noteitem, "25/12/2020")
            }
            if(checkPriority.isChecked && noteitem == ""){
                newItem = Item(nameitem,0, 1,"", "25/12/2020")
            }
            newItem = Item(nameitem,0, 0,"", "25/12/2020")
        }

        val data = Intent().apply {
            putExtra(ITEM, newItem!!)
        }
        setResult(Activity.RESULT_OK,data)
        finish()
    }
}
