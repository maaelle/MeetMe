package com.example.meetme;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class MainActivity_121 : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_121)

        val dataList = ArrayList<Data_121>()
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "1. Hi! I am in View 1"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "2. Hi! I am in View 2"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "3. Hi! I am in View 3"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "4. Hi! I am in View 4"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "5. Hi! I am in View 5"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "6. Hi! I am in View 6"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "7. Hi! I am in View 7"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "8. Hi! I am in View 8"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "9. Hi! I am in View 9"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "10. Hi! I am in View 10"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_ONE, "11. Hi! I am in View 11"))
        dataList.add(Data_121(Adapter_121.VIEW_TYPE_TWO, "12. Hi! I am in View 12"))

        val adapter = Adapter_121(this, dataList)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }




}