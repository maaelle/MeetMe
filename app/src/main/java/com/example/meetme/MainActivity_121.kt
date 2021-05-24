package com.example.meetme;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


// tenative de chat entre deux personnes : actuellement ne fonctionne pas
class MainActivity_121 : AppCompatActivity(), ConversationAdapterListener3, ConversationAdapterListener4{

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var message: EditText
    private val idconv = "1234"

    private val TAG = "ok"

    private val adapter = ConversationAdapter3(this)
    private val adapter2 = ConversationAdapter4(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_121)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance(Constant.DB_URL).reference

        message = findViewById(R.id.boxMessage)

        val id = auth.currentUser.uid


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val proposal = ArrayList<String>()
                val proposal2 = ArrayList<String>()
                val child  = dataSnapshot.children
                child.forEach {
                    val key = it.key
                    if (id != key){
                        val messagerecu  = it.getValue<String>()
                        messagerecu?.let { it1 -> proposal2.add(it1) }
                    }
                    else{
                        val messageenv  = it.getValue<String>()
                        messageenv?.let { it1 -> proposal.add(it1) }
                    }

                }

                populateRecycler(proposal)
                populateRecycler2(proposal2)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.child("conversation").child(idconv).addValueEventListener(postListener)

        setUpRecyclerView()

        val retourconv : ImageView = findViewById(R.id.imgRetour2)
        retourconv.setOnClickListener { retourconv() }

        val send : ImageView = findViewById(R.id.btnSendMessage)
        send.setOnClickListener { send() }

    }

    private fun retourconv(){
        val intentconv = Intent(this, MainActivity_chat::class.java)
        startActivity(intentconv)
    }

    private fun send(){
        val mess = message.text.toString()
        val id = auth.currentUser.uid

        database.child("conversation").child(idconv).child(id).setValue(mess)

    }

    private fun setUpRecyclerView() {
        val recyclerView: RecyclerView=findViewById(R.id.recycler_view_121)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    private fun populateRecycler(proposal: ArrayList<String>) {
        adapter.setData(proposal)
    }

    private fun populateRecycler2(proposal2: ArrayList<String>) {
        adapter2.setData(proposal2)
    }

    override fun onUserClicked(utilisateur: String) {
            return
    }

}

interface ConversationAdapterListener3{
    fun onUserClicked(name: String)
}




class ConversationAdapter3(private val listener: MainActivity_121) : RecyclerView.Adapter<ConversationAdapter3.ViewHolder>() {
    private var data: ArrayList<String> = ArrayList()
    fun setData(data: ArrayList<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.text121)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_121_me, parent, false)
        )


    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pretendant = data[position]
        holder.text.text = pretendant.capitalize(Locale.getDefault())
        holder.itemView.setOnClickListener { listener.onUserClicked(pretendant) }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}

interface ConversationAdapterListener4{
    fun onUserClicked(name: String)
}


class ConversationAdapter4(private val listener: MainActivity_121) : RecyclerView.Adapter<ConversationAdapter4.ViewHolder>() {
    private var data: ArrayList<String> = ArrayList()
    fun setData(data: ArrayList<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.textenv)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_121_p, parent, false)
        )


    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pretendant = data[position]
        holder.text.text = pretendant.capitalize(Locale.getDefault())
        holder.itemView.setOnClickListener { listener.onUserClicked(pretendant) }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}