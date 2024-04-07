package com.example.task2_characterlist

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task2_characterlist.adapter.CharacterAdapter
import com.example.task2_characterlist.dataModel.ModelDataClassItem
import com.example.task2_characterlist.viewModel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dialogInfo:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.rv_character)
        dialogInfo= Dialog(this)

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
        characterAdapter= CharacterAdapter(this,dataList,object :CharacterAdapter.OnCardClickListener{
            override fun onCardClick(item: ModelDataClassItem) {
                detailsDialogue(item)
                Log.d("TAG", "onCardClick:$item ")
            }

        })
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.apply {
            setHasFixedSize(true)
            adapter=characterAdapter
        }
    }

    private fun detailsDialogue(item: ModelDataClassItem) {
        dialogInfo.setContentView(R.layout.item_details_dialouge_layout)
        dialogInfo.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogInfo.setCancelable(false)
        dialogInfo.window?.attributes?.windowAnimations = R.style.animation
        val btnCancel=dialogInfo.findViewById<ImageView>(R.id.img_cancel)
        val imgProfile=dialogInfo.findViewById<ImageView>(R.id.img_profile)
        val fullName=dialogInfo.findViewById<TextView>(R.id.tv_first_name)
        val dateOfBirth=dialogInfo.findViewById<TextView>(R.id.tv_date_of_birth)
        val gender=dialogInfo.findViewById<TextView>(R.id.tv_gender)
        val hairColor=dialogInfo.findViewById<TextView>(R.id.tv_hair_color)
        val house=dialogInfo.findViewById<TextView>(R.id.tv_house)
        val species=dialogInfo.findViewById<TextView>(R.id.tv_species)
        val ancestry=dialogInfo.findViewById<TextView>(R.id.tv_ancestry)
        val patronus=dialogInfo.findViewById<TextView>(R.id.tv_patronus)
        val wand=dialogInfo.findViewById<TextView>(R.id.tv_wand)

        btnCancel.setOnClickListener {
            dialogInfo.dismiss()
        }

        Glide.with(this).load(item.image).error(R.drawable.profile).placeholder(R.drawable.profile).into(imgProfile)
//        fullName.setText("Name: ${item.name?:""}")
//        dateOfBirth.text="Date Of Birth: ${item.dateOfBirth?:""}"
//        gender.text="Gender: ${item.gender?:""}"
//        hairColor.text="Hair Color: ${item.hairColour?:""}"
//        house.text="House: ${item.house?:""}"
//        species.text="Species: ${item.species?:""}"
//        ancestry.text="Ancestry: ${item.ancestry?:""}"
//        patronus.text="Patronus: ${item.patronus?:""}"
//        wand.text=

        dialogInfo.show()
    }
}