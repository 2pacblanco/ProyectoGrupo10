package com.example.proyecto_todolist_grupo10

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_todolist_grupo10.model.Users

class MyProfileActivity : AppCompatActivity() {

    companion object{

        var loginuser: Users? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile_activity)
        supportActionBar?.hide()

        loginuser = intent.getSerializableExtra("logUser") as? Users

        val twName = findViewById<TextView>(R.id.twnameuser)
        val twEmail = findViewById<TextView>(R.id.twemailuser)
        val twPhone = findViewById<TextView>(R.id.twphoneuser)
        twName.text = loginuser!!.name
        twEmail.text = loginuser!!.email
        twPhone.text = loginuser!!.phone

    }

    fun onBack(view: View){

        var intent = Intent(this,UserInfoActivity::class.java)
        intent.putExtra("logUser", UserInfoActivity.loginuser)
        startActivity(intent)
    }

}