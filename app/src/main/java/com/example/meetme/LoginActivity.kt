package com.example.meetme


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var EmailConnect : EditText
    private lateinit var PasswordConnect : EditText
    private val TAG = "mon appli"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        EmailConnect= findViewById(R.id.connectEmail)
        PasswordConnect= findViewById(R.id.connectPassword)

        val register: TextView = findViewById(R.id.register)
        register.setOnClickListener{connection()}

        val connect : Button = findViewById(R.id.connect)
        connect.setOnClickListener{connecter()}

    }

    private fun connection(){
        val intent6 = Intent(this, InscriptionActivity::class.java)
        startActivity(intent6)
    }

    private fun connecter(){ // on utilise comme pour InscriptionActivity l'authentification de firebase
        val email = EmailConnect.text.toString()
        val password = PasswordConnect.text.toString()

        if (TextUtils.isEmpty(email)){
            EmailConnect.error = "Email is required"
        }

        if (TextUtils.isEmpty(password)){
            PasswordConnect.error = "Password is required"
        }


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }


    }

    private fun updateUI(user: FirebaseUser?) {
        val intent7 = Intent(this, CompteActivity::class.java)
        startActivity(intent7)
    }
}