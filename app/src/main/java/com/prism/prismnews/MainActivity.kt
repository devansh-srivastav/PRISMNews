package com.prism.prismnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val items = fetchdata()
        val adapter:NewsListAdapter = NewsListAdapter(items, this)
        recyclerView.adapter = adapter


    }

    private fun fetchdata(): ArrayList<String> {
        val list = ArrayList<String>()
        for(i in 1 until 101){
            list.add("Item $i")
        }
        return list
    }

    override fun onItemClicked(item: String) {
        Toast.makeText(this, "Clicked item is $item", Toast.LENGTH_LONG).show()
    }

}