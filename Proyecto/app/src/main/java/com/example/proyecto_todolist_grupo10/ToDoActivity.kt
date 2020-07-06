package com.example.proyecto_todolist_grupo10

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_todolist_grupo10.configuration.RestApiService
import com.example.proyecto_todolist_grupo10.model.*
import com.example.proyecto_todolist_grupo10.networking.HerokuApi
import com.example.proyecto_todolist_grupo10.networking.HerokuApiService
import kotlinx.android.synthetic.main.activity_to_do.*
import kotlinx.android.synthetic.main.custom_dialog_name.*
import kotlinx.android.synthetic.main.custom_dialog_share.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class ToDoActivity : AppCompatActivity() {

    companion object{
        lateinit var btnlisto: Button
        var tempList : Lists? = null
        var loguser : Users? = null
        var list : Lists? = null
        lateinit var items_usuario : ArrayList<Item>
        lateinit var complete_items : ArrayList<Item>
        lateinit var item_id_aux : String
        var state = 0

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        supportActionBar?.hide()

        items_usuario = ArrayList<Item>()
        complete_items = ArrayList<Item>()

        btnlisto = findViewById<Button>(R.id.btCompletar)
        btnlisto.visibility = View.GONE


        list = intent.getSerializableExtra("List") as Lists
        loguser = intent.getSerializableExtra("user_log") as Users

        println(list.toString())

        recycler_view.adapter = HistoricAdapter2(items_usuario!!)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        (recycler_view.adapter as HistoricAdapter2).setDataset(items_usuario!!)

        recycler_view2.adapter = HistoricAdapter3(complete_items)
        recycler_view2.layoutManager = LinearLayoutManager(this)
        (recycler_view2.adapter as HistoricAdapter3).setDataset(complete_items)


        val request = HerokuApiService.buildService(HerokuApi::class.java)

        val call = request.getItemsbyList(list!!.id)
        call.enqueue(object : Callback<ArrayList<Item>> {
            override fun onResponse(
                call: Call<ArrayList<Item>>,
                response: Response<ArrayList<Item>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val items = response.body()!!
                        items.forEach {
                            println("$it  --> Agregada al usuario")
                            if (!it.done) {
                                items_usuario.add(it)
                            }
                            if (it.done){
                                complete_items.add(it)
                            }
                            recycler_view.adapter?.notifyDataSetChanged()
                            recycler_view2.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Item>>, t: Throwable) {
                Toast.makeText(this@ToDoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })



        val twName = findViewById<TextView>(R.id.twNameList)
        twName.text = list!!.name
        val hipertext: TextView = findViewById(R.id.MostrarCompletados)


        hipertext.setOnClickListener {
            if (recycler_view2.visibility == View.VISIBLE) { //si es Visible lo pones Gone
                recycler_view2.visibility = View.GONE
                hipertext.text = "Mostrar Completados"
            } else { // si no es Visible, lo pones
                recycler_view2.visibility = View.VISIBLE;
                hipertext.text = "Ocultar Completados"
            }

        }


        btnlisto.setOnClickListener {
            val newlist1 = (recycler_view.adapter as HistoricAdapter2).getDataset()
            val newlist2 = ArrayList<Item>()
            newlist1.forEach {
                if (it.check == 1) {
                    it.done = true
                    item_id_aux = it.id
                    var aux_item1 = aux_item2(
                        it.name,
                        it.position,
                        it.list_id,
                        it.starred.toString(),
                        it.due_date,
                        it.notes,
                        it.done.toString()
                    )
                    val request = HerokuApiService.buildService(HerokuApi::class.java)
                    val call = request.updateItem(aux_item1, item_id_aux)
                    call.enqueue(object : Callback<Item> {
                        override fun onResponse(call: Call<Item>, response: Response<Item>) {
                            if (response.isSuccessful) {
                                if (response.body() != null) {
                                    val items = response.body()!!
                                    println("SIII SI SE COMPLETYAAA")
                                    println(items)
                                }
                            }
                        }

                        override fun onFailure(call: Call<Item>, t: Throwable) {
                            Toast.makeText(this@ToDoActivity, "${t.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })

                }
            }
            Toast.makeText(
                applicationContext,
                "Items completados existosamente!!",
                Toast.LENGTH_SHORT
            ).show()
            //podemos ahorrarnos este código si hacemos el notifydatasetchanged
            //pero no es recomendable , ya aue si tiramos de nuevo la actividad tendremos los items completados ejejjeje
            var intent = Intent(this, ToDoActivity::class.java)
            intent.putExtra("user_log", loguser)
            intent.putExtra("List", list)
            startActivity(intent)
            //else{
            //  newlist2.add(it)
            //}
        }

        //
        //(recycler_view.adapter as HistoricAdapter2).setDataset(newlist2)
        //recycler_view.adapter?.notifyDataSetChanged()


        btNewItem.setOnClickListener {
            startActivityForResult(AddItem.newInstance1(this), 1)
        }


        //agregar funcionalidad del botón compartir
        btShare.setOnClickListener{
            var email_to_share : String = ""

            //abrir un pequeño dialog para meter el email, verificar tbm si es formato email
            val dialog = Dialog(it.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
           //crear el layout custom para el nuevo dialog
            dialog.setContentView(R.layout.custom_dialog_share)


            val cancelButton: Button = dialog.findViewById(R.id.btnCancel) as Button
            val confirmButton: Button = dialog.findViewById(R.id.btnConfirm) as Button
            cancelButton.setOnClickListener(View.OnClickListener { dialog.dismiss() })
            confirmButton.setOnClickListener(View.OnClickListener {
                email_to_share = dialog.etNewName1.text.toString()
                if (!validarEmail(email_to_share)){
                    dialog.etNewName1.error = "Email no válido"
                    dialog.etNewName1.setText("")
                }
                else{
                    var aux_list = share_a_list1(list!!.id, email_to_share )
                    println(aux_list)
                    //ocupar el método post
                    val apiService = RestApiService()
                    apiService.shareaList(aux_list){ it1 ->
                        if(it1 != null){
                            println("Lista compartida exitosamente!! -->>  $it1")
                            Toast.makeText(this, "Lista compartida existosamente con $email_to_share !!",Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(this, "El email : $email_to_share , no pertenece a nuestra aplicación. No se pudo compartir la lista.",Toast.LENGTH_LONG).show()
                            dialog.etNewName1.setText("")
                            dialog.show()
                        }

                    }
                    dialog.dismiss()
                }
            })
            dialog.show()



        }

        recycler_view2.visibility = View.GONE




    }
    fun onBack(view: View){
        var intent = Intent(this,WelcomeActivity::class.java)
        intent.putExtra("logUser", loguser)
        startActivity(intent)
    }

    private fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    //agregar función y correcta subida a la api de compartir lista con un mail
    



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( resultCode == Activity.RESULT_OK) {
            if(requestCode==1){
                println("entramos al requestCode == 1")
                data?.apply {
                    var item = getSerializableExtra(AddItem.ITEM) as Item
                    item.list_id = list!!.id
                    var auxItem = aux_item(item.name, item.position,item.list_id, item.starred.toString(), item.due_date, item.notes, item.lat , item.long)

                    val items = ArrayList<aux_item>()
                    items.add(auxItem)
                    val item1 = Items(items)
                    println(item1)

                    //hacer post a la api y asi poder verificar si funciona el get
                    val apiService = RestApiService()
                    apiService.addItems(item1){ it1 ->
                        println(it1.toString())
                        it1!!.forEach {
                            items_usuario.add(it)
                        }
                        recycler_view.adapter?.notifyDataSetChanged()
                    }
                    
                }
            }

        }


    }

}