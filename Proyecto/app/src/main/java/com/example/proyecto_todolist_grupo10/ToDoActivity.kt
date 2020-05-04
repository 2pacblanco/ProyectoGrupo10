package com.example.proyecto_todolist_grupo10

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_todolist_grupo10.MainActivity.Companion.CONTACT
import kotlinx.android.synthetic.main.activity_to_do.*

class ToDoActivity : AppCompatActivity() {

    companion object{
        var nameList : Listas? = null
        var lista_actividades = mutableListOf<Item>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        supportActionBar?.hide()

        val list: Listas = intent.getSerializableExtra("nameList") as Listas


        recycler_view.adapter = HistoricAdapter2(list.items)
        recycler_view.layoutManager = LinearLayoutManager(this)





        val twName = findViewById<TextView>(R.id.twNameList)
        twName.text = list.name


    }

    fun onBack(view: View){
        var intent = Intent(this,WelcomeActivity::class.java)
        startActivity(intent)
    }

}