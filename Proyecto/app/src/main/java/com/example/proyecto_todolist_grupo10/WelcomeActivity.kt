package com.example.proyecto_todolist_grupo10

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_todolist_grupo10.MainActivity.Companion.CONTACT
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportActionBar?.hide()

        val user: Users = intent.getSerializableExtra(CONTACT) as Users


        recycler_view.adapter = HistoricAdapter1(user.UsersLists)
        recycler_view.layoutManager = LinearLayoutManager(this)


        val ivUsers = findViewById<ImageView>(R.id.imageviewUser)

        val userPhotoId = this.resources.getIdentifier("descarga", "drawable", packageName)
        ivUsers.setImageResource(userPhotoId)

        val twName = findViewById<TextView>(R.id.twnameuser)
        twName.text = user.name


    }

    fun onLogout(view: View){
        var intent1 = Intent(this,MainActivity::class.java)
        startActivity(intent1)
    }


}


