package com.example.proyecto_todolist_grupo10


import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_todolist_grupo10.model.Item
import kotlinx.android.synthetic.main.cells_complete_items.view.*
import java.util.*


class HistoricAdapter3 (private val completeItems: ArrayList<Item>):
    RecyclerView.Adapter<HistoricAdapter3.HistoricViewHolder>() {

    private lateinit var dataset: ArrayList<Item>

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): HistoricViewHolder{
        val item = LayoutInflater.from(parent.context).inflate((R.layout.cells_complete_items),parent,false)
        return HistoricViewHolder(item)

    }

    override fun getItemCount(): Int {
        return completeItems.count()
    }

    override fun onBindViewHolder(holder: HistoricViewHolder, position: Int) {
        val currentItem = completeItems[position]
        holder.bindHistoric(currentItem)

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
            view.twNameCompleteItem.text = item.name
            view.imageViewCompleteItemPriority.visibility = View.GONE

            view.twNameCompleteItem.paintFlags = view.twNameCompleteItem.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            view.checkBoxItem2.isChecked = true

            if(item.starred == true){
                view.imageViewCompleteItemPriority.visibility = View.VISIBLE
            }

        }


    }




}

