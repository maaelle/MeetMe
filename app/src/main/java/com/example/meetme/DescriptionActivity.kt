package com.example.meetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.example.meetme.Constant.Companion.DB_URL
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class DescriptionActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth

    private lateinit var desc : EditText
    private lateinit var cit : EditText
    private lateinit var hobbies : EditText

    private val TAG = "ça marche"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        database = FirebaseDatabase.getInstance(DB_URL).reference
        auth = Firebase.auth

        desc = findViewById(R.id.EnterDescription)
        cit = findViewById(R.id.EnterCitation)
        hobbies = findViewById(R.id.EnterHobbies)

        remplissage()

        val validation: Button = findViewById(R.id.validation)
        validation.setOnClickListener { ReturnAccount()}
    }

    private fun ReturnAccount(){
        update()

        val intent4 = Intent(this, CompteActivity::class.java)
        startActivity(intent4)
    }

    private fun remplissage(){
        val userId = auth.currentUser.uid

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue<Utilisateur>()
                desc.setText(post?.description)
                cit.setText(post?.citation)
                hobbies.setText(post?.hobbies)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.child("users").child(userId).addValueEventListener(postListener)

    }

    private fun update() {
        val description=desc.text.toString()
        val citation=cit.text.toString()
        val hobbies=hobbies.text.toString()


        val id=auth.currentUser.uid


        database.child("users").child(id).child("description").setValue(description)
        database.child("users").child(id).child("citation").setValue(citation)
        database.child("users").child(id).child("hobbies").setValue(hobbies)

    }

}
