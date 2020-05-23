package com.example.proyecto_todolist_grupo10

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_todolist_grupo10.model.Users
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    companion object{
        var logUser : Users? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val user: Users? = intent.getSerializableExtra("usuario_conect") as? Users

        if (user != null){
            logUser = user
        }
    }

    fun onLogin(view: View){
        val email: String = etMail.text.toString()
        val pass: String = etPassword.text.toString()
        if(logUser == null){
            var intent = Intent(this,WelcomeActivity::class.java)
            startActivity(intent)
        }
        else{
            var intent = Intent(this,WelcomeActivity::class.java)
            intent.putExtra("logUser",logUser)
            startActivity(intent)

        }

    }

}