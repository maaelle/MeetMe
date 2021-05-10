package com.example.meetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val validation: Button = findViewById(R.id.validation)
        validation.setOnClickListener { ReturnAccount()}
    }

    private fun ReturnAccount(){
        val intent4 = Intent(this, CompteActivity::class.java)
        startActivity(intent4)
    }
}