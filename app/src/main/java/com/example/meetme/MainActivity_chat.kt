package com.example.meetme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

// tentative de création de liste de chat
class MainActivity_chat : AppCompatActivity(), ConversationAdapterListener2 {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val TAG = "ok"

    private val adapter = ConversationAdapter2(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chat)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance(Constant.DB_URL).reference

        val id = auth.currentUser.uid
        val proposal = ArrayList<String>()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                val child = dataSnapshot.children
                child.forEach {
                    val key = it.key
                    if (id != key) { // le problème est ici, on peut obtenir une liste de cette manière mais les destinatires sont représentés par leur id et non leur nom, si on met leur nom tout plante
                        key?.let { it1 -> proposal.add(it1) }
                    }
                }
                populateRecycler(proposal)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }

        }

        database.child("users").child(id).child("correspondant").addValueEventListener(postListener)

        setUpRecyclerView()

        val retour : ImageView = findViewById(R.id.imgRetour)
        retour.setOnClickListener { retour() }

    }

    private fun retour(){
        val intentret = Intent(this, ChoiceActivity::class.java)
        startActivity(intentret)
    }

    private fun setUpRecyclerView() {
        val recycler_view: RecyclerView = findViewById(R.id.recycler_view_chat)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }

    private fun populateRecycler(proposal: ArrayList<String>) {
        adapter.setData(proposal)
    }


    override fun onUserClicked(utilisateur: String) {
        val conv : ImageView = findViewById(R.id.image_view_click)
        conv.setOnClickListener { chat() }
        return
    }

    private fun chat(){
        val intentchat = Intent(this, MainActivity_121::class.java)
        startActivity(intentchat)
    }
}

    interface ConversationAdapterListener2{
        fun onUserClicked(name: String)
    }




    class ConversationAdapter2(private val listener: MainActivity_chat) : RecyclerView.Adapter<ConversationAdapter2.ViewHolder>() {
        private var data: ArrayList<String> = ArrayList()
        fun setData(data: ArrayList<String>) {
            this.data = data
            notifyDataSetChanged()
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val Nom : TextView = view.findViewById(R.id.text_view_1)

        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
            )


        }

        @ExperimentalStdlibApi
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val pretendant = data[position]
            holder.Nom.text = pretendant.capitalize(Locale.getDefault())
            holder.itemView.setOnClickListener { listener.onUserClicked(pretendant) }
        }
        override fun getItemCount(): Int {
            return data.size
        }

}
