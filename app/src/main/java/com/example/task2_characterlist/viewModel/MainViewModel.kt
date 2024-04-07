package com.example.task2_characterlist.viewModel

import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task2_characterlist.dataModel.ModelDataClassItem
import com.example.task2_characterlist.networking.ApiInstance
import com.example.task2_characterlist.networking.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel:ViewModel() {
    var liveDataList:MutableLiveData<MutableList<ModelDataClassItem>> = MutableLiveData()

    fun getCharacters():MutableLiveData<MutableList<ModelDataClassItem>>{
        return liveDataList
    }

    fun makeApiCall(progress: ContentLoadingProgressBar) {
        progress.show()
        val retroInstance= ApiInstance.buildApi()
        val retroService=retroInstance.create(ApiInterface::class.java)
        val call=retroService.getCharacterList()
        call.enqueue(object : Callback<MutableList<ModelDataClassItem>> {
            override fun onResponse(
                call: Call<MutableList<ModelDataClassItem>>,
                response: Response<MutableList<ModelDataClassItem>>
            ) {
                progress.hide()
                liveDataList.postValue(response.body())
            }

            override fun onFailure(call: Call<MutableList<ModelDataClassItem>>, t: Throwable) {
                liveDataList.postValue(null)
                progress.hide()
            }

        })

    }
}