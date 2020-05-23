package com.example.proyecto_todolist_grupo10

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_todolist_grupo10.model.Item
import kotlinx.android.synthetic.main.custom_dialog_name.*
import kotlinx.android.synthetic.main.to_do_cells.view.*


class HistoricAdapter2 (private val toDoList: List<Item>):
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
            view.twNameToDoActivity.text = item.name


        }


    }




}

