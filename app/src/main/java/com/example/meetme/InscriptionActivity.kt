package com.example.meetme


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth as auth

class InscriptionActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var Email: EditText
    private lateinit var Password : EditText
    private lateinit var Name : EditText
    private var TAG = "mon appli"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        auth = Firebase.auth
        database = Firebase.database.reference


        val create : Button = findViewById(R.id.create)
        create.setOnClickListener{inscrire()}
    }

    fun inscrire(){

        Email = findViewById(R.id.editEmail)
        Password = findViewById(R.id.editPassword)
        Name = findViewById(R.id.editName)
        val email = Email.toString()
        val password = Password.toString()

        if (TextUtils.isEmpty(email)){
            Email.error = "Email is required"
        }

        if (TextUtils.isEmpty(password)){
            Password.error = "Password is required"
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload()
        }
    }

    private fun reload() {
        auth.currentUser.reload()
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                if (task.isSuccessful) {
                    updateUI(auth.currentUser)
                } else {
                    Log.e(TAG, "reload", task.exception)
                }
            })
    }


    private fun updateUI(user: FirebaseUser?) {
        val intent8 = Intent(this, CompteActivity::class.java)
        startActivity(intent8)

        val identifiant=auth.currentUser.uid
        val email = Email.toString()
        val name = Name.toString()

        writeNewUser(identifiant, email, name )
    }

    fun writeNewUser(userId: String, email: String, name: String) {

        val user = Utilisateur(userId, email, name)
        database.child("users").child(userId).setValue(user)
    }
}