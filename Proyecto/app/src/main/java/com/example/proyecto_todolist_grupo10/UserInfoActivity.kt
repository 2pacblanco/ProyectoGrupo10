package com.example.proyecto_todolist_grupo10

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.proyecto_todolist_grupo10.model.Item
import com.example.proyecto_todolist_grupo10.model.Lists
import com.example.proyecto_todolist_grupo10.model.Users

class UserInfoActivity : AppCompatActivity(){

    companion object{

        var loginuser: Users? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_info_activity)
        supportActionBar?.hide()

        loginuser = intent.getSerializableExtra("logUser") as? Users

        val twName = findViewById<TextView>(R.id.twnameuser)
        val twEmail = findViewById<TextView>(R.id.twemailuser)
        var image = findViewById<ImageView>(R.id.twimageviewUser)

        Glide.with(this)
            .load(WelcomeActivity.loginuser!!.profile_photo)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(image)

        twName.text = loginuser!!.name +" "+ loginuser!!.last_name
        twEmail.text = loginuser!!.email

    }

    fun onBack(view: View){

        var intent = Intent(this,WelcomeActivity::class.java)
        intent.putExtra("logUser", loginuser)
        startActivity(intent)
    }

    fun onLogout(view: View){
        var intent = Intent(this,MainActivity::class.java)
        intent.putExtra("usuario_conect", WelcomeActivity.loginuser)
        startActivity(intent)
    }

    fun onProfile(view: View){
        var intent = Intent(this,MyProfileActivity::class.java)
        intent.putExtra("logUser", loginuser)
        startActivity(intent)
    }
}