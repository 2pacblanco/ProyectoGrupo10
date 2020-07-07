package com.example.proyecto_todolist_grupo10

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyecto_todolist_grupo10.model.Users
import com.example.proyecto_todolist_grupo10.model.aux_user
import com.example.proyecto_todolist_grupo10.networking.HerokuApi
import com.example.proyecto_todolist_grupo10.networking.HerokuApiService
import kotlinx.android.synthetic.main.custom_dialog_change_name_user.*
import kotlinx.android.synthetic.main.custom_dialog_change_phone_user.*
import kotlinx.android.synthetic.main.my_profile_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyProfileActivity : AppCompatActivity() {

    companion object{

        var loginuser: Users? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile_activity)
        supportActionBar?.hide()

        loginuser = intent.getSerializableExtra("logUser") as? Users

        val twName = findViewById<TextView>(R.id.twnameuser)
        val twEmail = findViewById<TextView>(R.id.twemailuser)
        val twPhone = findViewById<TextView>(R.id.twphoneuser)
        val imageView = findViewById<ImageView>(R.id.image1)

        twName.text = loginuser!!.name +" "+ loginuser!!.last_name
        twEmail.text = loginuser!!.email
        twPhone.text = loginuser!!.phone

        btnChangeName.setOnClickListener{
            val dialog = Dialog(it.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog_change_name_user)
            val cancelButton: Button = dialog.findViewById(R.id.btnCancel) as Button
            val confirmButton: Button = dialog.findViewById(R.id.btnConfirm) as Button
            cancelButton.setOnClickListener { dialog.dismiss() }
            confirmButton.setOnClickListener {
                loginuser!!.name = dialog.etNewNameUser.text.toString()
                twName.text = dialog.etNewNameUser.text.toString() +" "+ loginuser!!.last_name
                dialog.dismiss()
            }
            dialog.show()
        }
        btnChangePhone.setOnClickListener{
            val dialog = Dialog(it.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog_change_phone_user)
            val cancelButton: Button = dialog.findViewById(R.id.btnCancel) as Button
            val confirmButton: Button = dialog.findViewById(R.id.btnConfirm) as Button
            cancelButton.setOnClickListener { dialog.dismiss() }
            confirmButton.setOnClickListener {
                val checkNumber = dialog.etNewPhone.text.toString()
                var numeric = true
                numeric = checkNumber.matches("-?\\d+(\\.\\d+)?".toRegex())
                if (numeric){
                    if (checkNumber.length == 8) {
                        twPhone.text = "+569 "+ dialog.etNewPhone.text.toString()
                        loginuser!!.phone = twPhone.text as String
                        dialog.dismiss()
                    }
                    else{
                        Toast.makeText(this, "Largo del numero debe ser 8", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
                else{
                    Toast.makeText(this, "No es un numero valido", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
        Glide.with(this)
            .load(loginuser!!.profile_photo)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(imageView);


    }

    fun onBack(view: View){
        println(loginuser)
        //guardar el loginuser en la api con update_self
        var auxUser = aux_user(loginuser!!.email, loginuser!!.name, loginuser!!.last_name, loginuser!!.phone, loginuser!!.profile_photo)
        val request = HerokuApiService.buildService(HerokuApi::class.java)
        val call = request.updateUser(auxUser)
        call.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val items = response.body()!!
                        println("Usuario updateado milagrosamente")
                        println(items)
                    }
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@MyProfileActivity, "${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        WelcomeActivity.loginuser = loginuser!!
        var intent = Intent(this,UserInfoActivity::class.java)
        intent.putExtra("logUser", loginuser)
        startActivity(intent)
    }

}