package com.example.task2_characterlist

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
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
    private lateinit var progress:ContentLoadingProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.rv_character)
        progress=findViewById(R.id.loader)

        dialogInfo= Dialog(this)

        //ViewModel
        val viewModel: MainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getCharacters().observe(this,Observer{
            if (it != null){
              setAdapter(it)
            }else{
                Toast.makeText(this,"Please Check Your Internet!!",Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall(progress)
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
        dialogInfo.window?.setBackgroundDrawable(ColorDrawable(0x00000000))
        val btnCancel=dialogInfo.findViewById<ImageView>(R.id.img_cancel)
        val imgProfile=dialogInfo.findViewById<ImageView>(R.id.img_profile)
        val gender=dialogInfo.findViewById<ImageView>(R.id.img_gender)
        val hogwartsStudent=dialogInfo.findViewById<ImageView>(R.id.img_hogwartsStudent)
        val alive=dialogInfo.findViewById<ImageView>(R.id.img_alive)
        val wizrd=dialogInfo.findViewById<ImageView>(R.id.img_wizard)

        val fullName=dialogInfo.findViewById<TextView>(R.id.tv_full_name)
        val dateOfBirth=dialogInfo.findViewById<TextView>(R.id.tv_date_of_birth)

        val hairColor=dialogInfo.findViewById<TextView>(R.id.tv_haircolor_value)
        val species=dialogInfo.findViewById<TextView>(R.id.tv_species_value)
        val ancestry=dialogInfo.findViewById<TextView>(R.id.tv_ancestry_value)
        val patronus=dialogInfo.findViewById<TextView>(R.id.tv_patronus_value)
        val eyeColor=dialogInfo.findViewById<TextView>(R.id.tv_eyecolor_value)

        btnCancel.setOnClickListener {
            dialogInfo.dismiss()
        }

        Glide.with(this).load(item.image).error(R.drawable.profile).placeholder(R.drawable.profile).into(imgProfile)

        if (!gender.isVisible)gender.visibility=View.VISIBLE
        when(item.gender){
            "male"->  Glide.with(this).load(R.drawable.male_svg).error(R.drawable.ic_launcher_foreground).into(gender)
            "female"-> Glide.with(this).load(R.drawable.female_svg).error(R.drawable.ic_launcher_foreground).into(gender)
            "others"-> Glide.with(this).load(R.drawable.transgender_svg).error(R.drawable.ic_launcher_foreground).into(gender)
            else-> gender.visibility=View.GONE
        }
        fullName.text =item.name?:""

        dateOfBirth.text=item.dateOfBirth?:""
        hairColor.text=item.hairColour?:""
        species.text=item.species?:""
        ancestry.text=item.ancestry?:""
        patronus.text=item.patronus?:""
        eyeColor.text=item.eyeColour?:""
        if (item.hogwartsStudent==true){
            Glide.with(this).load(R.drawable.checkmark_svg).error(R.drawable.ic_launcher_foreground).into(hogwartsStudent)
        }else Glide.with(this).load(R.drawable.wrong_svg).error(R.drawable.ic_launcher_foreground).into(hogwartsStudent)

        if (item.alive==true){
            Glide.with(this).load(R.drawable.checkmark_svg).error(R.drawable.ic_launcher_foreground).into(alive)
        }else Glide.with(this).load(R.drawable.wrong_svg).error(R.drawable.ic_launcher_foreground).into(alive)

        if (item.wizard==true){
            Glide.with(this).load(R.drawable.checkmark_svg).error(R.drawable.ic_launcher_foreground).into(wizrd)
        }else Glide.with(this).load(R.drawable.wrong_svg).error(R.drawable.ic_launcher_foreground).into(wizrd)

        dialogInfo.show()
    }
}