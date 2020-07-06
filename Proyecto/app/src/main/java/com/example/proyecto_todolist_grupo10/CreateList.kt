package com.example.proyecto_todolist_grupo10

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.proyecto_todolist_grupo10.MainActivity.Companion.fusedLocationClient1
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import kotlinx.android.synthetic.main.activity_create_list.*
import java.time.LocalDate

class CreateList : AppCompatActivity() {

    companion object{
        const val  LIST = "LIST"
        fun newInstance(context: Context) = Intent(context, CreateList::class.java)
        var latitude1 : Double = 0.0
        var longitude1 : Double = 0.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_list)
        supportActionBar?.hide()

    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    fun createList(view: View) {


        var inputname = inputNameOfList.text.toString()


        var items = ArrayList<Item>()
        /*

        fusedLocationClient1.lastLocation
            .addOnSuccessListener { location : Location? ->
                latitude1 = location!!.latitude
                longitude1 = location!!.longitude
                println(latitude1.toString() + longitude1.toString())
                val item1 = Item("item creado automáticamente 1", 0, 0, "Bueno, esta es la nota generada automaticamente al crear la lista",LocalDate.now().plusDays(30), LocalDate.now(),0,latitude1,longitude1)
                val item2 = Item("item creado automáticamente 2", 0, 0, "Bueno, esta es la nota generada automaticamente al crear la lista",LocalDate.now().plusDays(30), LocalDate.now(),0,latitude1,longitude1)
                val item3 = Item("item creado automáticamente 3", 0, 0, "Bueno, esta es la nota generada automaticamente al crear la lista",LocalDate.now().plusDays(30), LocalDate.now(),0,latitude1,longitude1)
                val item4 = Item("item creado automáticamente 4", 0, 0, "Bueno, esta es la nota generada automaticamente al crear la lista",LocalDate.now().plusDays(30), LocalDate.now(),0,latitude1,longitude1)
                items.add(item1)
                items.add(item2)
                items.add(item3)
                items.add(item4)*/

                if (inputname == ""){
                    Toast.makeText(this.applicationContext, "Ingrese nombre, es obligatorio para crear una lista", Toast.LENGTH_SHORT).show()
                }
                else{
                    val list = Lists("1",inputname, "1","asda","asdasd", items,
                        ArrayList<Item>(), false)
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
