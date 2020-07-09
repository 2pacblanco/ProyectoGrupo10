package com.example.proyecto_todolist_grupo10

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.proyecto_todolist_grupo10.ItemDetail.Companion.locationData
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.util.LocationUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class AddItem : AppCompatActivity() {

    companion object{
        const val  ITEM = "ITEM"
        var newItem : Item? = null
        var resultDateString : String = ""
        fun newInstance1(context: Context) = Intent(context, AddItem::class.java)
        lateinit var fusedLocationClient2 : FusedLocationProviderClient

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        newItem = null
        fusedLocationClient2  = LocationServices.getFusedLocationProviderClient(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        supportActionBar?.hide()

        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val currentDate = sdf.format(Date())
        println(currentDate)

        //add days
        val c1 = Calendar.getInstance()
        c1.add(Calendar.DAY_OF_YEAR, 30)
        val sdf1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val resultDate = c1.time
        resultDateString = sdf1.format(resultDate)
        println(resultDateString)
    }


    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    fun createItem(view: View) {

        val nameitem :String = findViewById<EditText>(R.id.NameItem).text.toString()
        val noteitem : String = findViewById<EditText>(R.id.NoteItem).text.toString()
        val checkPriority : CheckBox = findViewById<CheckBox>(R.id.checkBoxPrioridad)

        //asignar latitude y longitude
        var latitude1 : Double  = 0.0
        var longitude1 : Double = 0.0

        if(nameitem == ""){
            Toast.makeText(this.applicationContext, "Ingrese nombre, es obligatorio para crear un item", Toast.LENGTH_SHORT).show()
        }
        else{


            if( checkPriority.isChecked && noteitem != ""){
                newItem = Item("", nameitem,"1","por_cambiar", true,false, resultDateString,LocalDate.now().toString(),noteitem,LocalDate.now().toString(),0 , 0.0,0.0)
            }
            if(!checkPriority.isChecked && noteitem != ""){
                newItem = Item("", nameitem,"1","por_cambiar", false,false, resultDateString,LocalDate.now().toString(),noteitem,LocalDate.now().toString(),0 , 0.0,0.0)
            }
            if(checkPriority.isChecked && noteitem == ""){
                newItem = Item("", nameitem,"1","por_cambiar", true,false, resultDateString,LocalDate.now().toString(),"",LocalDate.now().toString(),0 , 0.0,0.0)
            }
            if(!checkPriority.isChecked && noteitem == ""){
                newItem = Item("", nameitem,"1","por_cambiar", false,false, resultDateString,LocalDate.now().toString(),"",LocalDate.now().toString(),0 , 0.0,0.0)

            }

            fusedLocationClient2.lastLocation
                .addOnSuccessListener { location : Location? ->
                    latitude1 = location!!.latitude
                    longitude1 = location!!.longitude
                    println(latitude1.toString() + longitude1.toString())
                    newItem!!.lat = latitude1
                    newItem!!.long = longitude1
                    val data = Intent().apply {
                        putExtra(ITEM, newItem!!)
                    }
                    setResult(Activity.RESULT_OK,data)
                    finish()
                }
            }
    }

    fun cancel(view: View){
        finish()
    }
}
