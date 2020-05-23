package com.example.proyecto_todolist_grupo10

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import com.example.proyecto_todolist_grupo10.model.Users
import kotlinx.android.synthetic.main.activity_to_do.*
import kotlinx.android.synthetic.main.activity_to_do.recycler_view
import kotlinx.android.synthetic.main.activity_welcome.*
import java.util.*
import kotlin.collections.ArrayList

class ToDoActivity : AppCompatActivity() {

    companion object{
        var tempList : Lists? = null
        var loguser : Users? = null
        var list : Lists? = null

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        supportActionBar?.hide()


        list = intent.getSerializableExtra("List") as Lists
        loguser = intent.getSerializableExtra("user_log") as Users

        tempList = list

        recycler_view.adapter = HistoricAdapter2(tempList!!.items)
        recycler_view.layoutManager = LinearLayoutManager(this)
        (recycler_view.adapter as HistoricAdapter2).setDataset(tempList!!.items)
        var newList: ArrayList<Item> = (recycler_view.adapter as HistoricAdapter2).getDataset()


        tempList!!.items = newList
        val twName = findViewById<TextView>(R.id.twNameList)
        twName.text = tempList!!.name


    }



    fun onBack(view: View){
        var index = 0
        loguser!!.UsersLists.forEach{
            if(it.name == list!!.name) {
                println(index)
                loguser!!.UsersLists[index] = tempList!!
                return@forEach
            }
            index += 1
        }

        var intent = Intent(this,WelcomeActivity::class.java)
        intent.putExtra("logUser", loguser)
        startActivity(intent)
    }

    fun changeName(view: View){
        var intent = Intent(view.context, ChangeNameList::class.java)
        startActivity(intent)
    }



}