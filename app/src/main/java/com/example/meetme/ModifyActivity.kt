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

class ModifyActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth

    private lateinit var name : EditText
    private lateinit var age : EditText
    private lateinit var profession : EditText
    private lateinit var localisation : EditText
    private lateinit var music : EditText
    private lateinit var musicauthor : EditText
    private lateinit var book : EditText
    private lateinit var bookauthor : EditText
    private lateinit var sport : EditText
    private lateinit var food : EditText

    private val TAG = "ça marche"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance(DB_URL).reference


        // on récupère tous les champs à remplir

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

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //on charge toutes les informations de l'utilisateur connecté depuis la database et on les met sous la forme d'un utilisateur
                val post = dataSnapshot.getValue<Utilisateur>()

                // on met chaque info dans la bonne case
                name.setText(post?.name)
                age.setText(post?.age)
                profession.setText(post?.profession)
                localisation.setText(post?.localisation)
                music.setText(post?.music)
                musicauthor.setText(post?.musicauthor)
                book.setText(post?.book)
                bookauthor.setText(post?.bookauthor)
                sport.setText(post?.sport)
                food.setText(post?.dishes)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.child("users").child(userId).addValueEventListener(postListener)
    }

    private fun miseajour(){

        // on récupère les valeurs entrées
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


        // on met à jour chaque child de la base de données
        database.child("users").child(id).child("name").setValue(name)
        database.child("users").child(id).child("age").setValue(age)
        database.child("users").child(id).child("profession").setValue(profession)
        database.child("users").child(id).child("localisation").setValue(localisation)
        database.child("users").child(id).child("music").setValue(music)
        database.child("users").child(id).child("musicauthor").setValue(musicauthor)
        database.child("users").child(id).child("book").setValue(book)
        database.child("users").child(id).child("bookauthor").setValue(bookauthor)
        database.child("users").child(id).child("sport").setValue(sport)
        database.child("users").child(id).child("dishes").setValue(dishes)

    }




}
