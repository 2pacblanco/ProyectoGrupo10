package com.example.proyecto_todolist_grupo10

import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import kotlinx.android.synthetic.main.custom_dialog_name.*
import kotlinx.android.synthetic.main.to_do_cells.view.*
import java.util.*


class HistoricAdapter2 (private val toDoList: ArrayList<Item>):
    RecyclerView.Adapter<HistoricAdapter2.HistoricViewHolder>() {



    private lateinit var dataset: ArrayList<Item>

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): HistoricViewHolder{
        val item = LayoutInflater.from(parent.context).inflate((R.layout.to_do_cells),parent,false)
        return HistoricViewHolder(item)

    }

    override fun getItemCount(): Int {
        return toDoList.count()
    }

    override fun onBindViewHolder(holder: HistoricViewHolder, position: Int) {
        val currentItem = toDoList[position]
        holder.bindHistoric(currentItem)

        holder.itemView.btUpItem.setOnClickListener{
            if(position == 0){
                Toast.makeText(holder.itemView.context, "No se puede subir más", Toast.LENGTH_SHORT).show()
            }
            else{
                Collections.swap(toDoList, position , position -1)
                notifyItemMoved(position,position-1)
                dataset = toDoList
                notifyDataSetChanged()
            }

        }
        holder.itemView.btDownItem.setOnClickListener{
            if(position + 1 == toDoList.size){
                Toast.makeText(holder.itemView.context, "No se puede bajar más", Toast.LENGTH_SHORT).show()
            }
            else{
                Collections.swap(toDoList, position , position + 1)
                notifyItemMoved(position,position+1)
                dataset = toDoList
                notifyDataSetChanged()
            }

        }

        holder.itemView.btnChangeName.setOnClickListener{
            val dialog = Dialog(it.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog_name)
            val cancelButton: Button = dialog.findViewById(R.id.btnCancel) as Button
            val confirmButton: Button = dialog.findViewById(R.id.btnConfirm) as Button
            cancelButton.setOnClickListener(View.OnClickListener { dialog.dismiss() })
            confirmButton.setOnClickListener(View.OnClickListener {
                val etNewName: String = dialog.etNewName.text.toString()
                currentItem.name = etNewName
                notifyDataSetChanged()
                dialog.dismiss()
            })
            dialog.show()
        }

        holder.itemView.checkBoxItem.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                currentItem.check = 1
                dataset = toDoList
                notifyDataSetChanged()
            }else{
                currentItem.check = 0
                dataset = toDoList
                notifyDataSetChanged()
            }

            var index = 0
            toDoList.forEach {
                if(it.check == 1){
                    index =+1
                }
            }
            if(index>0){
                ToDoActivity.btnlisto.visibility = View.VISIBLE
            }
            if(index == 0){
                ToDoActivity.btnlisto.visibility = View.GONE
            }

        }

        holder.itemView.setOnClickListener{
            var intent = Intent(it.context, ItemDetail::class.java)
            intent.putExtra("Item",currentItem)
            intent.putExtra("Lists_usurer",ToDoActivity.tempList as Lists)
            intent.putExtra("user_log", ToDoActivity.loguser)
            it.context.startActivity(intent)
        }

    }
    fun setDataset(dadaist: ArrayList<Item>) {
        this.dataset = dadaist
        notifyDataSetChanged()
    }

    fun getDataset(): ArrayList<Item> {
        return dataset
    }


    class HistoricViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        private var item: Item? = null

        init{}



        fun bindHistoric(item: Item){
            this.item = item
            view.imageViewPrioridad.visibility = View.GONE
            view.twNameToDoActivity.text = item.name



            if(item.estado == 1){
                view.checkBoxItem.isChecked = true
            }
            if(item.prioridad == 1){
                view.imageViewPrioridad.visibility = View.VISIBLE
            }



        }


    }




}

