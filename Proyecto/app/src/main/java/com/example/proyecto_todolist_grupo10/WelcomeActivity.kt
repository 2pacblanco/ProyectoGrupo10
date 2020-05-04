package com.example.proyecto_todolist_grupo10

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_todolist_grupo10.MainActivity.Companion.CONTACT
import kotlinx.android.synthetic.main.activity_welcome.*
import java.io.*


class WelcomeActivity : AppCompatActivity() {

    companion object{
        var loginuser : Users? = null
        var listas_usuario = mutableListOf<Listas>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportActionBar?.hide()

        btToNewList.setOnClickListener {
            startActivityForResult(CreateList.newInstance(this), 1)
        }

        loginuser = intent.getSerializableExtra(CONTACT) as? Users
        val deleteList = intent.getSerializableExtra("lista_eliminada") as? Listas

        if (loginuser != null && deleteList == null) {
            listas_usuario = loginuser!!.UsersLists
            recycler_view.adapter = HistoricAdapter1(listas_usuario)
            recycler_view.layoutManager = LinearLayoutManager(this)

            (recycler_view.adapter as HistoricAdapter1).setDataset(listas_usuario)

            val ivUsers = findViewById<ImageView>(R.id.imageviewUser)
            val userPhotoId = this.resources.getIdentifier("descarga", "drawable", packageName)
            ivUsers.setImageResource(userPhotoId)
            val twName = findViewById<TextView>(R.id.twnameuser)
            twName.text = loginuser!!.name

            var newList: List<Listas> = (recycler_view.adapter as HistoricAdapter1).getDataset()

            loginuser!!.UsersLists = newList as MutableList<Listas>

            val fos: FileOutputStream =
                applicationContext.openFileOutput("usuario_conectado", Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(loginuser)
            os.close()
            fos.close()
        }

        if (deleteList != null && loginuser == null) {
            val fis: FileInputStream = applicationContext.openFileInput("usuario_conectado")
            val sis = ObjectInputStream(fis)
            loginuser = sis.readObject() as Users
            sis.close()
            fis.close()

            listas_usuario = loginuser!!.UsersLists
            recycler_view.adapter = HistoricAdapter1(listas_usuario)
            recycler_view.layoutManager = LinearLayoutManager(this)

            (recycler_view.adapter as HistoricAdapter1).setDataset(listas_usuario)

            if (listas_usuario.contains(deleteList)) {
                listas_usuario.remove(deleteList)
                recycler_view.adapter?.notifyDataSetChanged()
            }

            val ivUsers = findViewById<ImageView>(R.id.imageviewUser)
            val userPhotoId = this.resources.getIdentifier("descarga", "drawable", packageName)
            ivUsers.setImageResource(userPhotoId)
            val twName = findViewById<TextView>(R.id.twnameuser)
            twName.text = loginuser!!.name

            var newList: List<Listas> = (recycler_view.adapter as HistoricAdapter1).getDataset()

            loginuser!!.UsersLists = newList as MutableList<Listas>


            val fos: FileOutputStream =
                applicationContext.openFileOutput("usuario_conectado", Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(loginuser)
            os.close()
            fos.close()
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( resultCode == Activity.RESULT_OK) {
            data?.apply {
                var list = getSerializableExtra(CreateList.LIST) as Listas
                listas_usuario.add(list!!)
                recycler_view.adapter?.notifyDataSetChanged()
                loginuser!!.UsersLists = listas_usuario

                val fos: FileOutputStream =
                    applicationContext.openFileOutput("usuario_conectado", Context.MODE_PRIVATE)
                val os = ObjectOutputStream(fos)
                os.writeObject(loginuser)
                os.close()
                fos.close()
            }
        }

    }


    fun onLogout(view: View){

        var intent1 = Intent(this,MainActivity::class.java)
        intent1.putExtra("usuario_conect",loginuser)
        startActivity(intent1)

    }
}


