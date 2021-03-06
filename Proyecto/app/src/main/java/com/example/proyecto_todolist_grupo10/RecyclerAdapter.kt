package com.example.proyecto_todolist_grupo10

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_todolist_grupo10.configuration.api_key
import com.example.proyecto_todolist_grupo10.model.Lists
import com.example.proyecto_todolist_grupo10.networking.HerokuApi
import com.example.proyecto_todolist_grupo10.networking.HerokuApiService
import kotlinx.android.synthetic.main.historic_cell.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HistoricAdapter1 (private val historicList: ArrayList<Lists>):
    RecyclerView.Adapter<HistoricAdapter1.HistoricViewHolder>(){

    private lateinit var dataset: ArrayList<Lists>


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
        holder.itemView.btDeleteList.setOnClickListener{
            val builder = AlertDialog.Builder(it.context)
            builder.setCancelable(false)
            builder.setTitle("Alerta")
            builder.setMessage("Desea Eliminar?")

            builder.setPositiveButton(android.R.string.yes){ _, _ ->
                historicList.remove(currentItem)
                dataset = historicList
                notifyDataSetChanged()

                val request = HerokuApiService.buildService(HerokuApi::class.java)

                val call = request.deleteGist(currentItem.id)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val lists =  response.body()!!
                                println(lists.toString())

                            }
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(it.context, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            builder.setNegativeButton(android.R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()


        }

    }

    fun setDataset(dadaist: ArrayList<Lists>) {
        this.dataset = dadaist
        notifyDataSetChanged()
    }

    fun getDataset(): ArrayList<Lists> {
        return dataset
    }


    class HistoricViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        private var item: Lists? = null

        init{}

        fun bindHistoric(item: Lists){
            this.item = item
            view.twNameofList.text = item.name
            view.imageView1.visibility = View.GONE

            if(item.shared){
                view.imageView1.visibility = View.VISIBLE
            }

            view.setOnClickListener{
                var intent = Intent(view.context, ToDoActivity::class.java)
                intent.putExtra("List",item)
                intent.putExtra("user_log", WelcomeActivity.loginuser)
                view.context.startActivity(intent)
            }




        }

    }


}



