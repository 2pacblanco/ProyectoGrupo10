package com.example.proyecto_todolist_grupo10

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_todolist_grupo10.configuration.api_key
import com.example.proyecto_todolist_grupo10.model.Users
import com.example.proyecto_todolist_grupo10.networking.HerokuApi
import com.example.proyecto_todolist_grupo10.networking.HerokuApiService
import com.example.proyecto_todolist_grupo10.util.LocationUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val mAuth : FirebaseAuth by lazy {FirebaseAuth.getInstance()}
    private val RC_GOOGLE_SIGN_IN = 99

    private lateinit var mGoogleSignInClient: GoogleSignInClient


    companion object{
        var logUser : Users? = null
        lateinit var fusedLocationClient1: FusedLocationProviderClient

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        fusedLocationClient1 = LocationServices.getFusedLocationProviderClient(this)
        ItemDetail.locationData = LocationUtil(this)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val user: Users? = intent.getSerializableExtra("usuario_conect") as? Users

        if (user != null){
            logUser = user
        }

        if (mAuth.currentUser == null){
            Toast.makeText(this, "Se ha cerrado sesion de la ultima vez", Toast.LENGTH_SHORT).show()
        }   else{
            Toast.makeText(this, "Usuario ya loggeado, se cerrara sesion", Toast.LENGTH_SHORT).show()
            mAuth.signOut()
        }

        //actualizado a la api de entrega 3
        val request = HerokuApiService.buildService(HerokuApi::class.java)

        val call = request.getUser(api_key)
        call.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val users =  response.body()!!
                        WelcomeActivity.loginuser.email = users.email
                        WelcomeActivity.loginuser.name = users.name
                        WelcomeActivity.loginuser.phone = users.phone
                        WelcomeActivity.loginuser.last_name = users.last_name
                        WelcomeActivity.loginuser.profile_photo = users.profile_photo
                    }
                }
            }
            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        //println(WelcomeActivity.loginuser.toString())



        btLogin.setOnClickListener(){
            val email = etMail.text.toString()
            val password = etPassword.text.toString()
            if ( isValidMailAndPassword(email, password)){
                logInByEmailAndPassword(email,password)
            }
        }

        logInGoogle.setOnClickListener(){
            view: View? -> signInGoogle()
        }
    }

    private fun signInGoogle(){
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Toast.makeText(this, "firebaseAuthWithGoogle:", Toast.LENGTH_SHORT).show()
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)
                }else{
                    Toast.makeText(this, "cuenta vacia", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "signInWithCredential:success", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this,WelcomeActivity::class.java)
                    intent.putExtra("logUser",logUser)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                    // ...
                }

                // ...
            }
    }


    private fun logInByEmailAndPassword(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                Toast.makeText(this, "LogIn successful", Toast.LENGTH_SHORT).show()
                var intent = Intent(this,WelcomeActivity::class.java)
                intent.putExtra("logUser",logUser)
                startActivity(intent)
            }else   {
                Toast.makeText(this, "Unexpected error happen, try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidMailAndPassword(email: String, password: String) : Boolean{
        return !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }




}


