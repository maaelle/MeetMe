package com.example.meetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class MainActivity_chat : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chat)



        val recycler_view: RecyclerView = findViewById(R.id.recycler_view_chat)
        recycler_view.adapter = Adapter_chat(liste())
        recycler_view.layoutManager = LinearLayoutManager(this)

        //val validation: Button = findViewById(R.id.button_id)
        //validation.setOnClickListener{ReturnAccount()}
    }

    private fun ReturnAccount() {
        val intent4= Intent( this, SelectionActivity::class.java)
        startActivity(intent4)
    }

    private fun liste(): ArrayList<Personne_chat> {

        val personne = arrayListOf<Personne_chat>()

        val name = resources.getStringArray(R.array.id);
        val message = resources.getStringArray(R.array.dernier_message);
        val images = R.drawable.profil;

        name.forEach {
            val item = Personne_chat(it, message.last(), images)
            personne += item
        }


        return personne
    }
}
