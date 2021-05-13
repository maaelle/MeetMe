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

class ModifyActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth

    private lateinit var name : TextInputEditText
    private lateinit var age : TextInputEditText
    private lateinit var profession : TextInputEditText
    private lateinit var localisation : TextInputEditText
    private lateinit var music : TextInputEditText
    private lateinit var musicauthor : TextInputEditText
    private lateinit var book : TextInputEditText
    private lateinit var bookauthor : TextInputEditText
    private lateinit var sport : TextInputEditText
    private lateinit var food : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        database = FirebaseDatabase.getInstance(DB_URL).reference
        auth = Firebase.auth

        name = findViewById(R.id.SelectNom)
        age = findViewById(R.id.SelectAge)
        profession = findViewById(R.id.job)
        localisation = findViewById(R.id.ville)
        music = findViewById(R.id.SelectMusic)
        musicauthor = findViewById(R.id.MusicAuthor)
        book = findViewById(R.id.SelectBook)
        bookauthor = findViewById(R.id.BookAuthor)
        sport = findViewById(R.id.SelectSport)
        food = findViewById(R.id.SelectFood)

        val next: Button = findViewById(R.id.next)
        next.setOnClickListener {changeWindowDesc()}

        info()

    }

    private fun changeWindowDesc(){
        miseajour()
        val intent3 = Intent(this, DescriptionActivity::class.java)
        startActivity(intent3)
    }

    private fun info(){

        val userId = auth.currentUser.uid

        val namedata = database.child("users").child(userId).get().result

        val user : Utilisateur
        user =namedata?.value as Utilisateur

        name.setText(user.name)
        age.setText(user.age)
        profession.setText(user.profession)
        localisation.setText(user.localisation)
        music.setText(user.music)
        musicauthor.setText(user.musicauthor)
        book.setText(user.book)
        bookauthor.setText(user.bookauthor)
        sport.setText(user.sport)
        food.setText(user.dishes)
    }

    private fun miseajour(){

        val name =name.text.toString()
        val age = age.text.toString()
        val profession = profession.text.toString()
        val localisation = localisation.text.toString()
        val music = music.text.toString()
        val musicauthor = musicauthor.text.toString()
        val book = book.text.toString()
        val bookauthor = bookauthor.text.toString()
        val sport =sport.text.toString()
        val dishes = food.text.toString()

        val id = auth.currentUser.uid


        val key = database.child("posts").push().key
        if (key == null) {
            Log.w("MeetMe", "Couldn't get push key for posts")
            return
        }

        val utilisateur = Utilisateur(id, name, age ,profession, localisation, music, musicauthor, book, bookauthor, sport, dishes)

        val postValues = utilisateur.toMap()


        val childUpdates = hashMapOf<String, Any>(
            "/posts/$key" to postValues,
            "/user-posts/$id/$key" to postValues
        )

        database.updateChildren(childUpdates)
    }




}
