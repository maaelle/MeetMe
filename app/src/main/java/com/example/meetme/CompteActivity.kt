package com.example.meetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.example.meetme.Constant.Companion.DB_URL
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
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

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue<Utilisateur>()
                name.text = post?.name
                age.text = post?.age
                profession.text = post?.profession
                localisation.text = post?.localisation
                music.text = post?.music
                musicauthor.text = post?.musicauthor
                book.text = post?.book
                bookauthor.text = post?.bookauthor
                sport.text = post?.sport
                food.text = post?.dishes
                desc.text = post?.description
                cit.text = post?.citation
                hobbies.text = post?.hobbies
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.child("users").child(userId).addValueEventListener(postListener)



    }

}