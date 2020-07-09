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
import com.example.proyecto_todolist_grupo10.configuration.RestApiService
import com.example.proyecto_todolist_grupo10.configuration.api_key
import com.example.proyecto_todolist_grupo10.model.*
import com.example.proyecto_todolist_grupo10.networking.ApiApi
import com.example.proyecto_todolist_grupo10.networking.ApiService
import com.example.proyecto_todolist_grupo10.networking.HerokuApi
import com.example.proyecto_todolist_grupo10.networking.HerokuApiService
import com.firebase.ui.auth.AuthUI
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
        var id_lists_share = ArrayList<String>()

    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempList = intent.getSerializableExtra("newList") as? ArrayList<Lists>

        val request2 = HerokuApiService.buildService(HerokuApi::class.java)

        val call2 = request2.getUser(api_key)
        call2.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val users =  response.body()!!
                        loginuser.email = users.email
                        loginuser.name = users.name
                        loginuser.phone = users.phone
                        loginuser.last_name = users.last_name
                        loginuser.profile_photo = users.profile_photo
                    }
                }
            }
            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@WelcomeActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


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
                        recycler_view.adapter = HistoricAdapter1(loginuser!!.UsersLists)
                        recycler_view.layoutManager = LinearLayoutManager(applicationContext)

                        (recycler_view.adapter as HistoricAdapter1).setDataset(loginuser!!.UsersLists)
                        }
                    }
                }
            override fun onFailure(call: Call<ArrayList<Lists>>, t: Throwable) {
                Toast.makeText(this@WelcomeActivity, "${t.message}", Toast.LENGTH_SHORT).show() }
        })

        //obtención de listas de shared list
        val call1 = request.getSharedLists()
        call1.enqueue(object : Callback<ArrayList<aux_shared_lists>> {
            override fun onResponse(call: Call<ArrayList<aux_shared_lists>>, response: Response<ArrayList<aux_shared_lists>>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val lists =  response.body()!!
                        lists.forEach {
                            if(!id_lists_share.contains(it.list_id)){
                                id_lists_share.add(it.list_id)
                            }
                            //ahora con it debemos ver la forma de sacar inmediatamente la lista correspondiente al id con GET LISTS/ID

                        }
                        println("Listas compartidas --> $id_lists_share ")
                        id_lists_share.forEach {
                            val call2 = request.getListbyId(it)
                            call2.enqueue(object : Callback<Lists> {
                                override fun onResponse(call: Call<Lists>, response: Response<Lists>) {
                                    if (response.isSuccessful) {
                                        if (response.body() != null) {
                                            var lists2 =  response.body()!!
                                            lists2.shared = true
                                            println("Lista generada desde shared : $lists2 ")
                                            loginuser!!.UsersLists.add(lists2)
                                            recycler_view.adapter?.notifyDataSetChanged()
                                        }
                                    }
                                }
                                override fun onFailure(call: Call<Lists>, t: Throwable) {
                                    Toast.makeText(this@WelcomeActivity, "${t.message}", Toast.LENGTH_SHORT).show() }
                            })

                        }


                        //loginuser!!.UsersLists = listas_usuario
                        //recycler_view.adapter = HistoricAdapter1(loginuser!!.UsersLists)
                        //recycler_view.layoutManager = LinearLayoutManager(applicationContext)

                        //(recycler_view.adapter as HistoricAdapter1).setDataset(loginuser!!.UsersLists)
                    }
                }
            }
            override fun onFailure(call: Call<ArrayList<aux_shared_lists>>, t: Throwable) {
                Toast.makeText(this@WelcomeActivity, "${t.message}", Toast.LENGTH_SHORT).show() }
        })



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

        btLogout.setOnClickListener{
            //firebase logout
            AuthUI.getInstance().signOut(this@WelcomeActivity)
                .addOnCompleteListener{
                    btLogout.isEnabled = false
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
        }

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
                    loginuser!!.UsersLists.add(it!!)
                    recycler_view.adapter?.notifyDataSetChanged()
                }
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

}


