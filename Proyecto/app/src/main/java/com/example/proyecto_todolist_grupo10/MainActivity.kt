package com.example.proyecto_todolist_grupo10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            //a actividad de inicio
            var intent = Intent(this,WelcomeActivity::class.java)
            startActivity(intent)
        }
    }

    //falta crear funcion para recuperar contraseña
    //falta crear funcion para registrase

}
