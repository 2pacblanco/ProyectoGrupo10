package com.example.proyecto_todolist_grupo10

import android.app.Activity
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

    companion object{
        var listas_usuario = mutableListOf<Listas>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportActionBar?.hide()

        btToNewList.setOnClickListener {
            startActivityForResult(CreateList.newInstance(this), 1)
        }

        val user = intent.getSerializableExtra(CONTACT) as Users

        listas_usuario = user.UsersLists


        recycler_view.adapter = HistoricAdapter1(listas_usuario)
        recycler_view.layoutManager = LinearLayoutManager(this)


        val ivUsers = findViewById<ImageView>(R.id.imageviewUser)

        val userPhotoId = this.resources.getIdentifier("descarga", "drawable", packageName)
        ivUsers.setImageResource(userPhotoId)

        val twName = findViewById<TextView>(R.id.twnameuser)
        twName.text = user.name


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( resultCode == Activity.RESULT_OK) {
            data?.apply {
                var list = getSerializableExtra(CreateList.LIST) as Listas
                listas_usuario.add(list!!)
                recycler_view.adapter?.notifyDataSetChanged()
            }
        }
    }


    fun onLogout(view: View){
        var intent1 = Intent(this,MainActivity::class.java)
        startActivity(intent1)
    }


}


