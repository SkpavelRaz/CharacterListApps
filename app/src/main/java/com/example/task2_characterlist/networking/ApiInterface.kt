package com.example.task2_characterlist.networking

import com.example.task2_characterlist.dataModel.ModelDataClassItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET(AppConstants.CHARACTER_LIST)
    fun getCharacterList(): Call<MutableList<ModelDataClassItem>>
}