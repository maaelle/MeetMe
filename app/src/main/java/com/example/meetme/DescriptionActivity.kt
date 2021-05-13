package com.example.meetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.example.meetme.Constant.Companion.DB_URL
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class DescriptionActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth

    private lateinit var desc : TextInputEditText
    private lateinit var cit : TextInputEditText
    private lateinit var hobbies : TextInputEditText

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

        val namedata = database.child("users").child(userId).get().result

        val user : Utilisateur

        user =namedata?.value as Utilisateur

        desc.setText(user.description)
        cit.setText(user.citation)
        hobbies.setText(user.hobbies)

    }

    private fun update(){
        val description =desc.text.toString()
        val citation = cit.text.toString()
        val hobbies = hobbies.text.toString()


        val id = auth.currentUser.uid


        val key = database.child("posts").push().key
        if (key == null) {
            Log.w("MeetMe", "Couldn't get push key for posts")
            return
        }

        val utilisateur = Utilisateur(id, hobbies, citation, description)

        val postValues = utilisateur.toMap()


        val childUpdates = hashMapOf<String, Any>(
                "/posts/$key" to postValues,
                "/user-posts/$id/$key" to postValues
        )

        database.updateChildren(childUpdates)

    }


}
