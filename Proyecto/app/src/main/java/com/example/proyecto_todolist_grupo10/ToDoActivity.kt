package com.example.proyecto_todolist_grupo10

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import com.example.proyecto_todolist_grupo10.model.Users
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_to_do.*
import kotlinx.android.synthetic.main.activity_to_do.recycler_view
import kotlinx.android.synthetic.main.activity_welcome.*
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ToDoActivity : AppCompatActivity() {

    companion object{
        lateinit var btnlisto: Button
        var tempList : Lists? = null
        var loguser : Users? = null
        var list : Lists? = null
        var complete_items = ArrayList<Item>()
        var state = 0

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        supportActionBar?.hide()

        btnlisto = findViewById<Button>(R.id.btCompletar)
        btnlisto.visibility = View.GONE


        list = intent.getSerializableExtra("List") as Lists
        loguser = intent.getSerializableExtra("user_log") as Users

        tempList = list



        val newlist1 = ArrayList<Item>()

        tempList!!.items.forEach{
            if(it.estado == 1){
                if(!tempList!!.itemsComplete!!.contains(it)){
                    tempList!!.itemsComplete!!.add(it)
                }

            }
            else{
                newlist1.add(it)
            }
        }

        tempList!!.items = newlist1

        recycler_view2.adapter = HistoricAdapter3(tempList!!.itemsComplete!!)
        recycler_view2.layoutManager = LinearLayoutManager(this)
        (recycler_view2.adapter as HistoricAdapter3).setDataset(complete_items)





        complete_items.forEach {
            println(it.name)
        }


        recycler_view.adapter = HistoricAdapter2(tempList!!.items)
        recycler_view.layoutManager = LinearLayoutManager(this)
        (recycler_view.adapter as HistoricAdapter2).setDataset(tempList!!.items)
        var newList: List<Item> = (recycler_view.adapter as HistoricAdapter2).getDataset()


        tempList!!.items = newList as ArrayList<Item>
        val twName = findViewById<TextView>(R.id.twNameList)
        twName.text = tempList!!.name




        val hipertext : TextView = findViewById(R.id.MostrarCompletados)


        hipertext.setOnClickListener{
            if(recycler_view2.visibility == View.VISIBLE){ //si es Visible lo pones Gone
                recycler_view2.visibility = View.GONE
                hipertext.text = "Mostrar Completados"
            }else{ // si no es Visible, lo pones
                recycler_view2.visibility = View.VISIBLE;
                hipertext.text = "Ocultar Completados"
            }

        }


        btnlisto.setOnClickListener{
            val newlist1 = ArrayList<Item>()
            tempList!!.items.forEach {
                if(it.check == 1){
                    it.estado = 1
                }
                else{
                    newlist1.add(it)
                }
            }

            //tempList!!.items = newlist1

            Toast.makeText(applicationContext, "Items completados existosamente!!", Toast.LENGTH_SHORT).show()
            var intent = Intent(this,ToDoActivity::class.java)
            intent.putExtra("user_log", loguser)
            intent.putExtra("List", tempList)
            startActivity(intent)
        }



        btNewItem.setOnClickListener {
            startActivityForResult(AddItem.newInstance1(this), 1)
        }






        recycler_view2.visibility = View.GONE


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( resultCode == Activity.RESULT_OK) {
            data?.apply {
                var item = getSerializableExtra(AddItem.ITEM) as Item
                tempList!!.items.add(item)
                recycler_view.adapter?.notifyDataSetChanged()

            }
        }

    }



    fun onBack(view: View){
        var index = 0
        loguser!!.UsersLists.forEach{
            if(it.name == list!!.name) {
                loguser!!.UsersLists[index] = tempList!!
                return@forEach
            }
            index += 1
        }

        var intent = Intent(this,WelcomeActivity::class.java)
        intent.putExtra("logUser", loguser)
        startActivity(intent)
    }





}