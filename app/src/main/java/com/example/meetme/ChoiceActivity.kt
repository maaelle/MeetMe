package com.example.meetme

import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList




class ChoiceActivity : AppCompatActivity(), ConversationAdapterListener{

    private lateinit var auth: FirebaseAuth
    private val adapter = ConversationAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        setContentView(R.layout.activity_choice)

        setUpRecyclerView()

        // C'est ici que je dois faire appel à firebase
        populateRecycler()

    }



    private fun setUpRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.adapter = adapter
    }
    private fun populateRecycler() {
        // ici on récupère dans la base de données un utilisateur à afficher.
        val pretendant = getRandomList()
        adapter.setData(pretendant)
    }
    private fun getRandomList(): ArrayList<Utilisateur> {
        val pretendant = arrayListOf<Utilisateur>()
        val userPicture = "https://picsum.photos/200"
        for (i in 0..100) {
            val name = "${getName()} ${getName()}"
            val age = "0${List(8) { ('0'..'9').random() }.joinToString("")}"
            val profession = "${getProfession()} ${getProfession()}"
            val localisation = "${getLocalisation()} ${getLocalisation()}"
            val music = "${getMusic()} ${getMusic()}"
            val musicauthor = "${getMusicAuthor()} ${getMusicAuthor()}"
            val book = "${getBook()} ${getBook()}"
            val bookauthor = "${getBookAuthor()} ${getBookAuthor()}"
            val sport = "${getSport()} ${getSport()}"
            val sportlevel = "${getSportLevel()} ${getSportLevel()}"
            val dishes = "${getDishes()} ${getDishes()}"
            val citation = "${getCitation()} ${getCitation()}"
            val description = "${getDescription()} ${getDescription()}"
            pretendant.add(Utilisateur(name, age, profession, localisation, music, musicauthor, book, bookauthor, sport, sportlevel, dishes, citation, description, userPicture))
        }
        return pretendant
    }
    private fun getName() = List(6) { ('a'..'z').random() }.joinToString("")
    private fun getProfession() = List(20) { ('a'..'z').random() }.joinToString("")
    private fun getLocalisation() = List(20) { ('a'..'z').random() }.joinToString("")
    private fun getMusic() = List(20) { ('a'..'z').random() }.joinToString("")
    private fun getMusicAuthor() = List(20) { ('a'..'z').random() }.joinToString("")
    private fun getBook() = List(20) { ('a'..'z').random() }.joinToString("")
    private fun getBookAuthor() = List(20) { ('a'..'z').random() }.joinToString("")
    private fun getSport() = List(20) { ('a'..'z').random() }.joinToString("")
    private fun getSportLevel() = List(20) { ('a'..'z').random() }.joinToString("")
    private fun getDishes() = List(20) { ('a'..'z').random() }.joinToString("")
    private fun getCitation() = List(20) { ('a'..'z').random() }.joinToString("")
    private fun getDescription() = List(20) { ('a'..'z').random() }.joinToString("")


    override fun onUserClicked(utilisateur: Utilisateur) {
        Toast.makeText(this, "You cliked on : ${utilisateur.name}", Toast.LENGTH_LONG).show()
        return
    }
}

interface ConversationAdapterListener{
    fun onUserClicked(name: Utilisateur)
}

class ConversationAdapter(private val listener: ChoiceActivity) : RecyclerView.Adapter<ConversationAdapter.ViewHolder>() {
    private var data: ArrayList<Utilisateur> = ArrayList()
    fun setData(data: ArrayList<Utilisateur>) {
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
        //holder.imgUserProfile.load(contact.userPicture)
        holder.itemView.setOnClickListener { listener.onUserClicked(pretendant) }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}


