package com.example.proyecto_todolist_grupo10

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_todolist_grupo10.model.Users
import kotlinx.android.synthetic.main.custom_dialog_change_name_user.*
import kotlinx.android.synthetic.main.custom_dialog_change_phone_user.*
import kotlinx.android.synthetic.main.custom_dialog_name.*
import kotlinx.android.synthetic.main.my_profile_activity.*

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
            cancelButton.setOnClickListener({ dialog.dismiss() })
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
            cancelButton.setOnClickListener({ dialog.dismiss() })
            confirmButton.setOnClickListener {
                loginuser!!.phone = dialog.etNewPhone.text.toString()
                twPhone.text = dialog.etNewNameUser.text.toString()
                dialog.dismiss()
            }
            dialog.show()
        }



    }

    fun onBack(view: View){
        var intent = Intent(this,UserInfoActivity::class.java)
        intent.putExtra("logUser", loginuser)
        startActivity(intent)
    }

}