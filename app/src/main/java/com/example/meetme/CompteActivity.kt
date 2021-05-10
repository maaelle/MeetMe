package com.example.meetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class CompteActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compte)

        auth = Firebase.auth

        //utilisateur()

        val returnbutton: ImageButton = findViewById(R.id.returnbutton)
        returnbutton.setOnClickListener{changeWindowSwipe()}

        val modifybutton: FloatingActionButton = findViewById(R.id.modifybutton)
        modifybutton.setOnClickListener{changeWindowModif()}
    }

    private fun changeWindowSwipe(){
        val intent = Intent(this, ChoiceActivity::class.java)
        startActivity(intent)
    }

    private fun changeWindowModif(){
        val intent2 = Intent(this, ModifyActivity::class.java )
        startActivity(intent2)
    }

    //private fun utilisateur(){
    //   val userId = auth.currentUser.uid

    //    mDatabase.child("users").child(userId).get().addOnSuccessListener {
    //        Log.i("firebase", "Got value ${it.value}")
    //    }.addOnFailureListener{
    //        Log.e("firebase", "Error getting data", it)
    //    }
    //}
}