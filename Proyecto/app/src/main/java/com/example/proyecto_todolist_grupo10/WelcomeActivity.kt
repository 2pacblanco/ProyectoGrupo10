package com.example.proyecto_todolist_grupo10

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_todolist_grupo10.MainActivity.Companion.fusedLocationClient1
import com.example.proyecto_todolist_grupo10.configuration.RestApiService
import com.example.proyecto_todolist_grupo10.configuration.api_key
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import com.example.proyecto_todolist_grupo10.model.ListsList
import com.example.proyecto_todolist_grupo10.model.Users
import com.example.proyecto_todolist_grupo10.networking.ApiApi
import com.example.proyecto_todolist_grupo10.networking.ApiService
import com.example.proyecto_todolist_grupo10.networking.HerokuApi
import com.example.proyecto_todolist_grupo10.networking.HerokuApiService
import kotlinx.android.synthetic.main.activity_welcome.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.time.LocalDate


class WelcomeActivity : AppCompatActivity() {

    companion object{
        var latitude1 : Double = 0.0
        var longitude1 : Double = 0.0
        var loginuser: Users = Users("","","","","","","","","", ArrayList<Lists>())
        var temp_listas = ArrayList<Lists>()

    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val temper : Users? = intent.getSerializableExtra("logUser") as? Users

        val tempList = intent.getSerializableExtra("newList") as? ArrayList<Lists>


        if(temper !=  null){
            loginuser = temper
            //println(loginuser.toString())

        }
        else{
            //sacar listas de la api
            var listas_usuario= ArrayList<Lists>()
            // GET LISTS --> lista de listas del usuario
            val request = HerokuApiService.buildService(HerokuApi::class.java)

            val call = request.getLists(api_key)
            call.enqueue(object : Callback<ArrayList<Lists>> {
                override fun onResponse(call: Call<ArrayList<Lists>>, response: Response<ArrayList<Lists>>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val lists =  response.body()!!
                            lists.forEach {
                                println("$it  --> Agregada al usuario")
                                listas_usuario.add(it)
                            }
                            loginuser!!.UsersLists = listas_usuario
                        }
                    }
                }
                override fun onFailure(call: Call<ArrayList<Lists>>, t: Throwable) {
                    Toast.makeText(this@WelcomeActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })


            //este código nos servirá para rellenar la location de los items sacados de la apí
            //val item1 = Item("item1", 0, 0, "Bueno, esta es la nota generada automaticamente al crear la lista", LocalDate.now().plusDays(30),LocalDate.now(),0,latitude1,longitude1)
            //val item2 = Item("item2", 0, 0, "Bueno, esta es la nota generada automaticamente al crear la lista",LocalDate.now().plusDays(30), LocalDate.now(),0,latitude1,longitude1)
            //var items = ArrayList<Item>()
            //fusedLocationClient1.lastLocation
              //  .addOnSuccessListener { location : Location? ->
                //    latitude1 = location!!.latitude
                  //  longitude1 = location!!.longitude
                    //println(latitude1.toString() + longitude1.toString())
                    //item1.latitude = latitude1
                    //item1.longitude = longitude1
                    //item2.latitude = latitude1
                    //item2.longitude = longitude1
                //}
            //items.add(item1)
            //items.add(item2)
            //hasta acá

            //acá creamos una lista, genérica, esto tenemos que rellenarlo con la info de la api
            println(loginuser.toString())

        }

        if (savedInstanceState != null) {
            loginuser = savedInstanceState.getSerializable("user_log") as Users
        }

        if(tempList != null){
            loginuser!!.UsersLists = tempList
        }

        setContentView(R.layout.activity_welcome)
        supportActionBar?.hide()

        btToNewList.setOnClickListener {
            startActivityForResult(CreateList.newInstance(this), 1)
        }

        temp_listas = loginuser!!.UsersLists
        recycler_view.adapter = HistoricAdapter1(temp_listas)
        recycler_view.layoutManager = LinearLayoutManager(this)

        (recycler_view.adapter as HistoricAdapter1).setDataset(temp_listas)

        val ivUsers = findViewById<ImageView>(R.id.imageviewUser1)
        val userPhotoId = this.resources.getIdentifier("descarga", "drawable", packageName)
        ivUsers.setImageResource(userPhotoId)
        val twName = findViewById<TextView>(R.id.twnameuser)
        twName.text = loginuser!!.name

        var newList: ArrayList<Lists> = (recycler_view.adapter as HistoricAdapter1).getDataset() as ArrayList<Lists>

        loginuser!!.UsersLists = newList



    }

    fun onProfile(view: View){
        var intent = Intent(this,UserInfoActivity::class.java)
        intent.putExtra("logUser", loginuser)
        startActivity(intent)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putSerializable("user_log", loginuser)
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( resultCode == Activity.RESULT_OK) {
            data?.apply {
                var list = getSerializableExtra(CreateList.LIST) as Lists
                //método post para ingresar lista a la api
                val apiService = RestApiService()

                apiService.addLists(list){
                    println(it.toString())
                }
                temp_listas.add(list!!)
                recycler_view.adapter?.notifyDataSetChanged()
                loginuser!!.UsersLists = temp_listas

            }
        }

    }

    override fun onPause() {
        super.onPause()
        val fos: FileOutputStream =
            applicationContext.openFileOutput("usuarios", Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(loginuser)
        os.close()
        fos.close()
    }

    fun onLogout(view: View){
        var intent = Intent(this,MainActivity::class.java)
        intent.putExtra("usuario_conect",loginuser)
        startActivity(intent)
    }

}


