package com.example.task2_characterlist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task2_characterlist.R
import com.example.task2_characterlist.dataModel.ModelDataClassItem

class CharacterAdapter(
    private val context: Context,
    private val dataList:MutableList<ModelDataClassItem>
):RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firstName: TextView = itemView.findViewById(R.id.tv_first_name)
        private val lastName: TextView = itemView.findViewById(R.id.tv_last_name)
        private val imageProfile: ImageView = itemView.findViewById(R.id.img_profile)

        fun bind(data: ModelDataClassItem) {
            val fullName=data.name?.split(" ")
            firstName.text="First Name: ${fullName?.firstOrNull()}"
            lastName.text="Last Name: ${fullName?.drop(1)?.joinToString(" ")}"

            Glide.with(context).load(data.image).placeholder(R.drawable.profile).error(R.drawable.profile).into(imageProfile)
        }
    }
}