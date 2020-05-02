package com.example.proyecto_todolist_grupo10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_welcome.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    companion object{
        const val  CONTACT = "CONTACT"
        var users = mutableListOf<Users>()
        var lists = mutableListOf<Listas>()
        var lists2 = mutableListOf<Listas>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createListofLists()
        createUsers()
        supportActionBar?.hide()


    }

    fun onLogin(view: View){
        val email: String = etMail.text.toString()
        val pass: String = etPassword.text.toString()

        if (email == "" ){
            Toast.makeText(applicationContext,"Mail no ingresado, Ingrese de nuevo porfavor",Toast.LENGTH_SHORT).show()
        }
        if(pass == ""){
            Toast.makeText(applicationContext,"Contraseña no ingresado, Ingrese de nuevo porfavor",Toast.LENGTH_SHORT).show()
        }
        else{
            var counter = 1
            users.forEach {
                if (it.email == email && it.pass== pass){
                    Toast.makeText(applicationContext, "Ingreso Correcto, bienvenido "+it.name, Toast.LENGTH_SHORT).show()
                    var intent = Intent(this,WelcomeActivity::class.java)
                    intent.putExtra(CONTACT,it)
                    startActivity(intent)
                    counter = 2
                }
            }
            if(counter == 1){
                Toast.makeText(applicationContext,"DATOS ERRÓNEOS, INGRESE NUEVAMENTE",Toast.LENGTH_SHORT).show()
            }
        }

    }




    fun createUsers(){
        if (users.isEmpty()){
            users.add(Users("jadonoso2@miuandes.cl","1234","Johnny Donoso",lists))
            users.add(Users("wena","xoro","El wenaxoro", lists2))
        }
    }


    fun createListofLists(){
        var item_prov = mutableListOf<Item>()
        item_prov.add(Item("item1"))
        item_prov.add(Item("item2"))
        item_prov.add(Item("item3"))
        item_prov.add(Item("item4"))
        item_prov.add(Item("item5"))
        var item_prov1 = mutableListOf<Item>()
        item_prov1.add(Item("item11"))
        item_prov1.add(Item("item22"))
        item_prov1.add(Item("item33"))
        item_prov1.add(Item("item44"))
        item_prov1.add(Item("item55"))
        var item_prov3 = mutableListOf<Item>()
        item_prov3.add(Item("item111"))
        item_prov3.add(Item("item222"))
        item_prov3.add(Item("item333"))
        item_prov3.add(Item("item444"))
        item_prov3.add(Item("item555"))

        if (lists.isEmpty()){
            lists.add(Listas(item_prov,"Lista1", 1))
            lists.add(Listas(item_prov1,"Lista2", 2))
        }
        if(lists2.isEmpty()){
            lists2.add(Listas(item_prov3,"Lista3",1))
        }



    }
    //falta crear funcion para recuperar contraseña
    //falta crear funcion para registrase

}



data class Users(val email: String, val pass: String, val name: String, val UsersLists: MutableList<Listas>): Serializable{}


data class Listas(val items: MutableList<Item>, val name: String, val position: Int): Serializable {}

data class Item(val name: String): Serializable{}