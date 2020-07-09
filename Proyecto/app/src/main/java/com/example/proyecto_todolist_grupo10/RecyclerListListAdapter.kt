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
import com.example.proyecto_todolist_grupo10.model.aux_item2
import com.example.proyecto_todolist_grupo10.networking.HerokuApi
import com.example.proyecto_todolist_grupo10.networking.HerokuApiService
import kotlinx.android.synthetic.main.custom_dialog_name.*
import kotlinx.android.synthetic.main.to_do_cells.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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


        holder.itemView.btnChangeName.setOnClickListener{ it1 ->
            val dialog = Dialog(it1.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog_name)
            val cancelButton: Button = dialog.findViewById(R.id.btnCancel) as Button
            val confirmButton: Button = dialog.findViewById(R.id.btnConfirm) as Button
            cancelButton.setOnClickListener(View.OnClickListener { dialog.dismiss() })
            confirmButton.setOnClickListener(View.OnClickListener {
                val etNewName: String = dialog.etNewName.text.toString()
                currentItem.name = etNewName
                //ocupar el método put
                var aux_item1 = aux_item2(
                    currentItem.name,
                    currentItem.position,
                    currentItem.list_id,
                    currentItem.starred.toString(),
                    currentItem.due_date,
                    currentItem.notes,
                    currentItem.done.toString()
                )
                val request = HerokuApiService.buildService(HerokuApi::class.java)
                val call = request.updateItem(aux_item1, currentItem.id)
                call.enqueue(object : Callback<Item> {
                    override fun onResponse(call: Call<Item>, response: Response<Item>) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val items = response.body()!!
                                println("SIII SI SE CAMBIA EL NOMBNREEE")
                                println(items)
                            }
                        }
                    }

                    override fun onFailure(call: Call<Item>, t: Throwable) {
                        Toast.makeText(it1.context, "${t.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                })

                notifyDataSetChanged()
                dialog.dismiss()
            })
            dialog.show()
        }

        holder.itemView.checkBoxItem.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                currentItem.check = 1
                dataset = toDoList
            }else{
                currentItem.check = 0
                dataset = toDoList
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
            intent.putExtra("list", ToDoActivity.list)
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
            item.check = 0

            if(item.done){
                view.checkBoxItem.isChecked = true
            }
            if(item.starred == true){
                view.imageViewPrioridad.visibility = View.VISIBLE
            }



        }


    }




}

