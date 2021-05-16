package com.example.meetme;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.collections.ArrayList

class MainActivity_121 : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var dataList = java.util.ArrayList<Data_121>()
    var topic = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_121)

        //val dataList = ArrayList<Data_121>()
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "1. Hi! I am in View 1"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "2. Hi! I am in View 2"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "3. Hi! I am in View 3"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "4. Hi! I am in View 4"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "5. Hi! I am in View 5"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "6. Hi! I am in View 6"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "7. Hi! I am in View 7"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "8. Hi! I am in View 8"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "9. Hi! I am in View 9"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "10. Hi! I am in View 10"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "11. Hi! I am in View 11"))
        //dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "12. Hi! I am in View 12"))

        val adapter = Adapter_121(this, dataList)
        recyclerView = findViewById(R.id.recycler_view_121)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        var intent = getIntent()
        var userId = intent.getStringExtra("userId")
        var userName = intent.getStringExtra("userName")

        imgRetour.setOnClickListener {
            onBackPressed()
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        btnSendMessage.setOnClickListener {
            var message: String = boxMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "message is empty", Toast.LENGTH_SHORT).show()
                boxMessage.setText("")
            } else {
                sendMessage(firebaseUser!!.uid, userId, message)
                boxMessage.setText("")
                topic = "/topics/$userId"
                PushNotification(NotificationData( userName!!,message),
                    topic).also {
                    sendNotification(it)
                }

            }
        }

        readMessage(firebaseUser!!.uid, userId)
    }

    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat").push().setValue(hashMap)

    }

    fun readMessage(senderId: String, receiverId: String) {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(Chat::class.java)

                    if (chat!!.senderId.equals(senderId) && chat!!.receiverId.equals(receiverId) ||
                        chat!!.senderId.equals(receiverId) && chat!!.receiverId.equals(senderId)
                    ) {
                        dataList.add(chat)
                    }
                }

                val Adapter_121 = Adapter_121(this@MainActivity_121, dataList)

                RecyclerView.adapter = Adapter_121
            }
        })
    }


    }




}