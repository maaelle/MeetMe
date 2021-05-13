package com.example.meetme

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.example.meetme.Constant.Companion.DB_URL
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*




class ChoiceActivity : AppCompatActivity(), ConversationAdapterListener{

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val TAG = "ok"

    private val adapter = ConversationAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance(DB_URL).reference

        val compte : FloatingActionButton = findViewById(R.id.compte)
        compte.setOnClickListener{retournecompte()}

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val proposal : List<Utilisateur> = dataSnapshot.getValue() as List<Utilisateur>
                if (proposal != null) {
                    populateRecycler(proposal)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(postListener)
        setContentView(R.layout.activity_choice)

        setUpRecyclerView()

        // C'est ici que je dois faire appel à firebase


    }

    private fun retournecompte(){
        val intcompte = Intent(this, CompteActivity::class.java)
        startActivity(intcompte)
    }

    private fun setUpRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.adapter = adapter
    }
    private fun populateRecycler(proposal: List<Utilisateur>) {
        // ici on récupère dans la base de données un utilisateur à afficher.
        adapter.setData(proposal)
    }

    override fun onUserClicked(utilisateur: Utilisateur) {
        Toast.makeText(this, "You cliked on : ${utilisateur.name}", Toast.LENGTH_LONG).show()
        return
    }
}

interface ConversationAdapterListener{
    fun onUserClicked(name: Utilisateur)
}

class ConversationAdapter(private val listener: ChoiceActivity) : RecyclerView.Adapter<ConversationAdapter.ViewHolder>() {
    private var data: List<Utilisateur> = arrayListOf()
    fun setData(data: List<Utilisateur>) {
        this.data = data
        notifyDataSetChanged()
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameliste: TextView = view.findViewById(R.id.nameliste)
        val ageliste: TextView = view.findViewById(R.id.ageliste)
        val metier: TextView = view.findViewById(R.id.metier)
        val localisationliste: TextView = view.findViewById(R.id.localistionliste)
        val citationliste: TextView = view.findViewById(R.id.citationliste)
        val descriptionliste: TextView = view.findViewById(R.id.descritpionliste)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)

        )
    }
    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pretendant = data[position]
        holder.nameliste.text = pretendant.name?.capitalize(Locale.getDefault())
        holder.ageliste.text = pretendant.age?.chunked(2)?.joinToString(separator = " ")
        holder.metier.text = pretendant.profession?.capitalize(Locale.getDefault())
        holder.localisationliste.text = pretendant.localisation?.capitalize(Locale.getDefault())
        holder.citationliste.text = pretendant.citation?.capitalize(Locale.getDefault())
        holder.descriptionliste.text = pretendant.description?.capitalize(Locale.getDefault())
        holder.itemView.setOnClickListener { listener.onUserClicked(pretendant) }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}


