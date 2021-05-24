package com.example.meetme

data class Utilisateur(val id: String? = null, val name: String? = null, val age: String? = null, val profession: String? = null, val localisation: String? = null, val music: String? = null, val musicauthor: String? = null, val book: String? = null, val bookauthor: String? = null, val sport: String? = null, val dishes: String? = null, val hobbies: String? = null, val citation: String? = null, val description: String? = null){


// data class représentant notre utilisateur et ses infos

    fun toMap(): Map<String, Any?> {
        return mapOf(
                "id" to id,
            "name" to name,
            "age" to age,
            "profession" to profession,
            "ville" to localisation,
            "book" to book,
            "bookauthor" to bookauthor,
            "music" to music,
            "musicauthor" to musicauthor,
            "sport" to sport,
            "dishes" to dishes,
            "hobbies" to hobbies,
            "citation" to citation,
            "description" to description
        )
    }


}