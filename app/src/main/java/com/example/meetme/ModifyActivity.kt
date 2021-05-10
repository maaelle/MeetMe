package com.example.meetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ModifyActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        database = Firebase.database.reference
        auth = Firebase.auth

        val next: Button = findViewById(R.id.next)
        next.setOnClickListener {changeWindowDesc()}

    }

    private fun changeWindowDesc(){
        miseajour()
        val intent3 = Intent(this, DescriptionActivity::class.java)
        startActivity(intent3)
    }



    private fun miseajour(){

        val Name : TextInputEditText = findViewById(R.id.SelectNom)
        val Age : EditText = findViewById(R.id.SelectAge)
        val Profession : TextInputEditText = findViewById(R.id.job)
        val Ville : TextInputEditText = findViewById(R.id.ville)
        val Book : TextInputEditText = findViewById(R.id.SelectBook)
        val BAuthor : TextInputEditText = findViewById(R.id.BookAuthor)
        val Music : TextInputEditText = findViewById(R.id.SelectMusic)
        val MAuthor : TextInputEditText = findViewById(R.id.MusicAuthor)
        val Food : TextInputEditText = findViewById(R.id.SelectFood)
        val Sport : TextInputEditText = findViewById(R.id.SelectSport)

        val name : String = Name.toString()
        val age : String = Age.toString()
        val job : String = Profession.toString()
        val ville : String = Ville.toString()
        val book : String = Book.toString()
        val bauthor : String = BAuthor.toString()
        val music : String = Music.toString()
        val mauthor : String = MAuthor.toString()
        val food : String = Food.toString()
        val sport : String = Sport.toString()

        val id = auth.currentUser.uid

        // recup email, password, description et citation
        val email = ""
        val password = ""

        val key = database.child("posts").push().key
        if (key == null) {
            Log.w("MeetMe", "Couldn't get push key for posts")
            return
        }

        val utilisateur = Utilisateur(id, email, password, name, age,job, ville, book, bauthor, music, mauthor, food, sport)

        val postValues = utilisateur.toMap()


        val childUpdates = hashMapOf<String, Any>(
            "/posts/$key" to postValues,
            "/user-posts/$id/$key" to postValues
        )

        database.updateChildren(childUpdates)
    }


}
