package com.example.proyecto_todolist_grupo10

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_todolist_grupo10.MainActivity.Companion.lists
import kotlinx.android.synthetic.main.historic_cell.view.*
import kotlin.collections.List


class HistoricAdapter1 (private val historicList: List<Listas>):
    RecyclerView.Adapter<HistoricAdapter1.HistoricViewHolder>() {

    //override se hace aun que devuelva null
    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): HistoricViewHolder{
        //layoutinflater, inserta item en la view padre(recyulcerview)
        val item = LayoutInflater.from(parent.context).inflate((R.layout.historic_cell),parent,false)
        return HistoricViewHolder(item)

    }

    override fun getItemCount(): Int {
        return historicList.count()
    }

    override fun onBindViewHolder(holder: HistoricViewHolder, position: Int) {
        val currentItem = historicList[position]
        holder.bindHistoric(currentItem)

    }


    class HistoricViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        private var item: Listas? = null

        init{}

        fun bindHistoric(item: Listas){
            this.item = item
            view.twNameofList.text = item.name

            view.setOnClickListener{
                Toast.makeText(view.context, "wena choro presionaste "+item.name, Toast.LENGTH_SHORT).show()
            }
        }

    }


}



