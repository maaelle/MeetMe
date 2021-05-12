package com.example.meetme


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.meetme.Constants.Companion.DB_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth as auth

class InscriptionActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var tvEmail: EditText
    private lateinit var tvpPassword: EditText
    private lateinit var tvName: EditText
    private var TAG = "InscriptionActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        tvEmail = findViewById(R.id.editEmail)
        tvpPassword = findViewById(R.id.editPassword)
        tvName = findViewById(R.id.editName)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance(DB_URL).reference

        findViewById<Button>(R.id.create).setOnClickListener { signUp() }
    }

    private fun signUp() {
        val email = tvEmail.text.toString()
        val password = tvpPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            this.tvEmail.error = "Email is required"
            return
        }

        if (TextUtils.isEmpty(password)) {
            this.tvpPassword.error = "Password is required"
            return
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
                    Toast.makeText(
                        baseContext, "Authentication failed : ${task.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun reload() {
        auth.currentUser?.reload()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUI(auth.currentUser)
                } else {
                    Log.e(TAG, "reload", task.exception)
                }
            }
    }


    private fun updateUI(user: FirebaseUser?) {
        val email = tvEmail.text.toString()
        val name = tvName.text.toString()

        user?.uid?.let { uidUser ->
            //un ?.let {} is like a  if uid != null
            writeNewUser(uidUser, email, name)
        }
    }

    private fun writeNewUser(userId: String, email: String, name: String) {
        val user = Utilisateur(userId, email, name)
        database.child("users").child(userId).setValue(user).addOnSuccessListener {
            Log.i(TAG, "Success !")
            val intent8 = Intent(this, CompteActivity::class.java)
            startActivity(intent8)
        }.addOnCanceledListener {
            Log.e(TAG, "Request canceled")
        }.addOnFailureListener {
            Log.e(TAG, "Something went wrong", it)
        }
    }
}