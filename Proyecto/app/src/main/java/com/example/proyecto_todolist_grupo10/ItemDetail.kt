package com.example.proyecto_todolist_grupo10

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import com.example.proyecto_todolist_grupo10.model.Users
import com.example.proyecto_todolist_grupo10.util.LocationUtil
import androidx.lifecycle.Observer
import com.example.proyecto_todolist_grupo10.model.aux_item2
import com.example.proyecto_todolist_grupo10.networking.HerokuApi
import com.example.proyecto_todolist_grupo10.networking.HerokuApiService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.custom_dialog_changenote.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class ItemDetail : AppCompatActivity() {

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        var ItemRecive: Item = Item("", "", "", "", false, false, "", "", "", "", 0, 0.0, 0.0)
        var loguser: Users? = null
        var tempList1: Lists? = null
        var cal: Calendar = Calendar.getInstance()
        var cal2: Calendar = Calendar.getInstance()
        lateinit var list : Lists
        lateinit var mMap: GoogleMap
        lateinit var locationData: LocationUtil
        var LOCATION_PERMISSION = 100

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        supportActionBar?.hide()

        imageViewPrioridadItem.visibility = View.GONE
        locationData = LocationUtil(this)


        ItemRecive = intent.getSerializableExtra("Item") as Item
        list = intent.getSerializableExtra("list") as Lists

        println("Item recibido en ITEM DETAIL  $ItemRecive")


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
        }
        invokeLocationAction(ItemRecive)

        //seteo de variables
        twNameItem.text = ItemRecive.name
        if(ItemRecive.starred){
            imageViewPrioridadItem.visibility = View.VISIBLE
        }
        if(!ItemRecive.starred){
            imageViewPrioridadItem.visibility = View.GONE
        }
        twFechaItem.setText(ItemRecive.due_date.toString())
        twNotasItem.text = ItemRecive.notes
        fechacreacion.text = "Creado el " + ItemRecive.create_at

        //edicion de fecha lo veremos en un rato
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        twFechaItem.setOnClickListener{
            val picker = DatePickerDialog(this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal2.get(Calendar.YEAR),
                cal2.get(Calendar.MONTH),
                cal2.get(Calendar.DAY_OF_MONTH))
            picker.show()
        }


        //edicion de prioridad
        lyPrioridad.setOnClickListener{
            var counter = 0
            if(!ItemRecive.starred  && counter == 0){
                imageViewPrioridadItem.visibility = View.VISIBLE
               ItemRecive.starred = true
                counter = 1
                println("prioridad del item cambio correctamente "+ItemRecive.name+" cambio a prioridad: "+ ItemRecive.starred.toString())
            }
            if(ItemRecive.starred && counter == 0){
                imageViewPrioridadItem.visibility = View.GONE
                ItemRecive.starred = false
                println("prioridad del item cambio correctamente "+ItemRecive.name+" cambio a prioridad: "+ ItemRecive.starred.toString())
                counter = 1
            }
            var aux_item1 = aux_item2(
                ItemRecive.name,
                ItemRecive.position,
                ItemRecive.list_id,
                ItemRecive.starred.toString(),
                ItemRecive.due_date,
                ItemRecive.notes,
                ItemRecive.done.toString()
            )
            val request = HerokuApiService.buildService(HerokuApi::class.java)
            val call = request.updateItem(aux_item1, ItemRecive.id)
            call.enqueue(object : Callback<Item> {
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val items = response.body()!!
                            println("SIII SI SE COMPLETYAAA")
                            println(items)
                        }
                    }
                }

                override fun onFailure(call: Call<Item>, t: Throwable) {
                    Toast.makeText(this@ItemDetail, "${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
            println("prioridad cambiada correctamente")
        }

        ContainerNotas.setOnClickListener{
            val dialog = Dialog(it.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog_changenote)
            val cancelButton: Button = dialog.findViewById(R.id.btnCancel1) as Button
            val confirmButton: Button = dialog.findViewById(R.id.btnConfirm1) as Button
            cancelButton.setOnClickListener(View.OnClickListener { dialog.dismiss() })
            confirmButton.setOnClickListener(View.OnClickListener {
                val NewNote: String = dialog.etNewNote.text.toString()
                ItemRecive.notes = NewNote
                var aux_item1 = aux_item2(
                    ItemRecive.name,
                    ItemRecive.position,
                    ItemRecive.list_id,
                    ItemRecive.starred.toString(),
                    ItemRecive.due_date,
                    ItemRecive.notes,
                    ItemRecive.done.toString()
                )
                val request = HerokuApiService.buildService(HerokuApi::class.java)
                val call = request.updateItem(aux_item1, ItemRecive.id)
                call.enqueue(object : Callback<Item> {
                    override fun onResponse(call: Call<Item>, response: Response<Item>) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val items = response.body()!!
                                println("SIII SI SE COMPLETYAAA")
                                println(items)
                            }
                        }
                    }

                    override fun onFailure(call: Call<Item>, t: Throwable) {
                        Toast.makeText(this@ItemDetail, "${t.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
                println("notas CAMBIADAS CORRECTAMENTE")
                dialog.dismiss()
                finish()
                startActivity(intent)
            })
            dialog.show()
        }

        btnDeleteItem.setOnClickListener{
            val builder = AlertDialog.Builder(it.context)
            builder.setCancelable(false)
            builder.setTitle("Alerta")
            builder.setMessage("Desea Eliminar?")

            builder.setPositiveButton(android.R.string.yes){ _, _ ->
                //ItemRecive eliminado  de la api y vuelta a toDoActivity
                val request = HerokuApiService.buildService(HerokuApi::class.java)

                val call = request.deleteItem(ItemRecive.id)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val lists =  response.body()!!
                                println(lists.toString())
                            }
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(it.context, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })

                var intent = Intent(this,ToDoActivity::class.java)
                intent.putExtra("user_log", ToDoActivity.loguser)
                intent.putExtra("List", list)
                startActivity(intent)
            }
            builder.setNegativeButton(android.R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()

        }

        btnCompleteItem.setOnClickListener{
            ItemRecive.done = true

            var aux_item1 = aux_item2(
                ItemRecive.name,
                ItemRecive.position,
                ItemRecive.list_id,
                ItemRecive.starred.toString(),
                ItemRecive.due_date,
                ItemRecive.notes,
                ItemRecive.done.toString()
            )
            val request = HerokuApiService.buildService(HerokuApi::class.java)
            val call = request.updateItem(aux_item1, ItemRecive.id)
            call.enqueue(object : Callback<Item> {
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val items = response.body()!!
                            println("SIII SI SE COMPLETYAAA")
                            println(items)
                        }
                    }
                }

                override fun onFailure(call: Call<Item>, t: Throwable) {
                    Toast.makeText(this@ItemDetail, "${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })

            Toast.makeText(it.context, "Item completado existosamente", Toast.LENGTH_SHORT).show()
            var intent = Intent(this,ToDoActivity::class.java)
            intent.putExtra("user_log", ToDoActivity.loguser)
            intent.putExtra("List", list)
            startActivity(intent)
        }


        btLogout1.setOnClickListener{
            var intent = Intent(this,ToDoActivity::class.java)
            intent.putExtra("user_log", ToDoActivity.loguser)
            intent.putExtra("List", list)
            startActivity(intent)
        }



    }

    private fun invokeLocationAction(item: Item) {
            when {
                isPermissionsGranted() -> locationData.observe(this, Observer {
                    println("${item.lat} , ${item.long}")

                    val polylineOptions = PolylineOptions().clickable(false).color(R.color.colorAccent).geodesic(true)
                        .width(10f)

                    mMap.addMarker(MarkerOptions().position(LatLng(item.lat, item.long)).title(item.name))
                    polylineOptions.add(LatLng(item.lat,item.long))
                    mMap.addPolyline(polylineOptions)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(item.lat,item.long), 12.0f))
                })

                shouldShowRequestPermissionRationale() -> println("Ask Permission")

                else -> ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    LOCATION_PERMISSION
                )
            }
        }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION -> {
                invokeLocationAction(ItemRecive)
            }
        }
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDateInView() {
        val myFormat = "yyyy-MM-dd HH:mm:ss" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        twFechaItem!!.setText(sdf.format(cal.time))

        if (Date() < cal.time) {
            ItemRecive.due_date = sdf.format(cal.time)
            //update ItemRecive pal put de los items
            var aux_item1 = aux_item2(
                ItemRecive.name,
                ItemRecive.position,
                ItemRecive.list_id,
                ItemRecive.starred.toString(),
                ItemRecive.due_date,
                ItemRecive.notes,
                ItemRecive.done.toString()
            )
            val request = HerokuApiService.buildService(HerokuApi::class.java)
            val call = request.updateItem(aux_item1, ItemRecive.id)
            call.enqueue(object : Callback<Item> {
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val items = response.body()!!
                            println("SIII SI SE COMPLETYAAA")
                            println(items)
                        }
                    }
                }

                override fun onFailure(call: Call<Item>, t: Throwable) {
                    Toast.makeText(this@ItemDetail, "${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
            Toast.makeText(this, "Fecha de plazo cambiada correctamente", Toast.LENGTH_SHORT).show()

        }
        else{
            twFechaItem!!.setText(ItemRecive.due_date)
            Toast.makeText(this, "no se pudo crear el nuevo plazo ya que es anterior a la fecha de hoy", Toast.LENGTH_SHORT).show()
        }

    }


}

