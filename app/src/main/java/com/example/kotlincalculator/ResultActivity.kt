package com.example.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private lateinit var userInfoList: ArrayList<UserInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        recyclerView = findViewById(R.id.recyclerViewList)
        userInfoList = ArrayList<UserInfo>()
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        adapter = RecyclerAdapter(userInfoList)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        if(userInfoList.isEmpty()){
            getData()
        }
    }

    private fun getData() {
        val db = AppDatabase(this)
        GlobalScope.launch {
            val data = db.userDao().getAll()
            data.forEach {
                runOnUiThread {
                    userInfoList.add(it)
                    adapter.notifyItemInserted(userInfoList.size - 1)
                }
            }
        }
    }
}