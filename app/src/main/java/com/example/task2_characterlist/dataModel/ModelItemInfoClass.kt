package com.example.task2_characterlist.dataModel

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class ModelItemInfoClass(
    val key:String,
    val value:String
):Serializable
