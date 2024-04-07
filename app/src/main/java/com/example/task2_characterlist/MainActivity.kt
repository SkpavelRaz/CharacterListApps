package com.example.task2_characterlist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task2_characterlist.adapter.CharacterAdapter
import com.example.task2_characterlist.dataModel.ModelDataClassItem
import com.example.task2_characterlist.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.rv_character)

        //ViewModel
        val viewModel: MainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getCharacters().observe(this,Observer{
            if (it != null){
              setAdapter(it)
            }else{
                Toast.makeText(this,"no data found!!",Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall()
    }

    private fun setAdapter(dataList: MutableList<ModelDataClassItem>) {
        characterAdapter= CharacterAdapter(this,dataList)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.apply {
            setHasFixedSize(true)
            adapter=characterAdapter
        }
    }
}