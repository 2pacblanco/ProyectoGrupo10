package com.example.proyecto_todolist_grupo10

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import com.example.proyecto_todolist_grupo10.model.Users
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.custom_dialog_changenote.*
import kotlinx.android.synthetic.main.custom_dialog_name.*

class ItemDetail : AppCompatActivity() {

    companion object{
        var ItemRecive : Item= Item("", 0, 0,"","",0)
        var loguser : Users? = null
        var tempList1 : Lists? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        supportActionBar?.hide()

        imageViewPrioridadItem.visibility = View.GONE


        ItemRecive = intent.getSerializableExtra("Item") as Item
        loguser = intent.getSerializableExtra("user_log") as Users
        tempList1 = intent.getSerializableExtra("Lists_usurer") as Lists

        //seteo de variables
        twNameItem.text = ItemRecive.name
        if(ItemRecive.prioridad==1){
            imageViewPrioridadItem.visibility = View.VISIBLE
        }
        if(ItemRecive.prioridad==0){
            imageViewPrioridadItem.visibility = View.GONE
        }
        twNotasItem.text = ItemRecive.notas

        //edicion de fecha lo veremos en un rato



        //edicion de prioridad
        lyPrioridad.setOnClickListener{
            var counter = 0
            if(ItemRecive.prioridad == 0 && counter == 0){
                imageViewPrioridadItem.visibility = View.VISIBLE
               ItemRecive.prioridad = 1
                counter = 1
                println("prioridad del item cambio correctamente "+ItemRecive.name+" cambio a prioridad: "+ ItemRecive.prioridad)
            }
            if(ItemRecive.prioridad == 1 && counter == 0){
                imageViewPrioridadItem.visibility = View.GONE
                ItemRecive.prioridad = 0
                println("prioridad del item cambio correctamente "+ItemRecive.name+" cambio a prioridad: "+ ItemRecive.prioridad)
                counter = 1
            }
        }

        ContainerNotas.setOnClickListener{
            val dialog = Dialog(it.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog_changenote)
            val cancelButton: Button = dialog.findViewById(R.id.btnCancel1) as Button
            val confirmButton: Button = dialog.findViewById(R.id.btnConfirm1) as Button
            cancelButton.setOnClickListener(View.OnClickListener { dialog.dismiss() })
            confirmButton.setOnClickListener(View.OnClickListener {
                val NewNote: String = dialog.etNewNote.text.toString()
                ItemRecive.notas = NewNote
                dialog.dismiss()
                finish()
                startActivity(intent)
            })
            dialog.show()
        }

        btnDeleteItem.setOnClickListener{
            val builder = AlertDialog.Builder(it.context)
            builder.setCancelable(false)
            builder.setTitle("Alerta")
            builder.setMessage("Desea Eliminar?")

            builder.setPositiveButton(android.R.string.yes){ _, _ ->
                var index1 = 0
                var tempitem : Item = Item("",0, 0, "","",0)
                tempList1!!.items.forEach{ it1 ->
                    if(it1.name == ItemRecive.name) {
                        tempitem = it1
                        return@forEach
                    }
                    index1 += 1
                }
                tempList1!!.items.remove(tempitem)

                var intent = Intent(this,ToDoActivity::class.java)
                intent.putExtra("user_log", loguser)
                intent.putExtra("List", tempList1)
                startActivity(intent)
            }
            builder.setNegativeButton(android.R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()

        }

        btnCompleteItem.setOnClickListener{
            ItemRecive.estado = 1
            Toast.makeText(it.context, "Item completado existosamente", Toast.LENGTH_SHORT).show()
            var index1 = 0
            tempList1!!.items.forEach{ it1 ->
                if(it1.name == ItemRecive.name) {
                    tempList1!!.items[index1] = ItemRecive
                    return@forEach
                }
                index1 += 1
            }

            var intent = Intent(this,ToDoActivity::class.java)
            intent.putExtra("user_log", loguser)
            intent.putExtra("List", tempList1)
            startActivity(intent)
        }




        println(loguser!!.name)
        println(tempList1!!.items[0].name)

        btLogout1.setOnClickListener{
            var index1 = 0
            tempList1!!.items.forEach{
                if(it.name == ItemRecive.name) {
                   tempList1!!.items[index1] = ItemRecive
                    return@forEach
                }
                index1 += 1
            }

            var intent = Intent(this,ToDoActivity::class.java)
            intent.putExtra("user_log", loguser)
            intent.putExtra("List", tempList1)
            startActivity(intent)
        }




    }
}
