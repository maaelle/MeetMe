package com.example.meetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.example.meetme.Constant.Companion.DB_URL
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class CompteActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference

    private lateinit var name : TextView
    private lateinit var age : TextView
    private lateinit var profession : TextView
    private lateinit var localisation : TextView
    private lateinit var music : TextView
    private lateinit var musicauthor :TextView
    private lateinit var book : TextView
    private lateinit var bookauthor : TextView
    private lateinit var sport : TextView
    private lateinit var food : TextView
    private lateinit var desc : TextView
    private lateinit var cit : TextView
    private lateinit var hobbies : TextView

    val TAG = "ok"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compte)

        name = findViewById(R.id.name)
        age = findViewById(R.id.age)
        profession = findViewById(R.id.profession)
        localisation = findViewById(R.id.localisation)
        music = findViewById(R.id.musique)
        musicauthor = findViewById(R.id.musiqueauthor)
        book = findViewById(R.id.book)
        bookauthor = findViewById(R.id.bookauthor)
        sport = findViewById(R.id.sport)
        food = findViewById(R.id.food)
        desc = findViewById(R.id.start_description)
        cit = findViewById(R.id.start_citation)
        hobbies = findViewById(R.id.start_hobbies)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance(DB_URL).reference

        utilisateur()

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

    private fun utilisateur(){

        val userId = auth.currentUser.uid

        val namedata = database.child("users").child(userId).get().result

        val user : Utilisateur

        user =namedata?.value as Utilisateur


        name.text=user.name
        age.text=(user.age)
        profession.text=(user.profession)
        localisation.text=(user.localisation)
        music.text=(user.music)
        musicauthor.text=(user.musicauthor)
        book.text=(user.book)
        bookauthor.text=(user.bookauthor)
        sport.text=(user.sport)
        food.text=(user.dishes)
        desc.text=(user.description)
        cit.text=(user.citation)
        hobbies.text=(user.hobbies)
    }

}