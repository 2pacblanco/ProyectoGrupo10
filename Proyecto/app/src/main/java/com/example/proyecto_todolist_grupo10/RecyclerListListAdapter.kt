package com.example.proyecto_todolist_grupo10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.historic_cell.view.*
import kotlin.collections.List


class HistoricAdapter2 (private val toDoList: List<Item>):
    RecyclerView.Adapter<HistoricAdapter2.HistoricViewHolder>() {

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

    }


    class HistoricViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        private var item: Item? = null

        init{}

        fun bindHistoric(item: Item){
            this.item = item
            view.twNameofList.text = item.name
        }


    }
}

