package com.example.proyecto_todolist_grupo10

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.historic_cell.view.*
import java.util.*


class HistoricAdapter1 (private val historicList: List<Listas>):
    RecyclerView.Adapter<HistoricAdapter1.HistoricViewHolder>(){

    private lateinit var dataset: List<Listas>


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

        holder.itemView.btUp.setOnClickListener{
            if(position == 0){
                Toast.makeText(holder.itemView.context, "No se puede subir más", Toast.LENGTH_SHORT).show()
            }
            else{
                Collections.swap(historicList, position , position -1)
                notifyItemMoved(position,position-1)
                dataset = historicList
                notifyDataSetChanged()
            }

        }
        holder.itemView.btDown.setOnClickListener{
            if(position + 1 == historicList.size){
                Toast.makeText(holder.itemView.context, "No se puede bajar más", Toast.LENGTH_SHORT).show()
            }
            else{
                Collections.swap(historicList, position , position + 1)
                notifyItemMoved(position,position+1)
                dataset = historicList
                notifyDataSetChanged()
            }

        }

    }

    fun setDataset(dadaist: List<Listas>) {
        this.dataset = dadaist
        notifyDataSetChanged()
    }

    fun getDataset(): List<Listas> {
        return dataset
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

            view.btDeleteList.setOnClickListener{
                val intent = Intent(view.context, WelcomeActivity::class.java)
                intent.putExtra("lista_eliminada",item)
                view.context.startActivity(intent)

            }


        }

    }


}



