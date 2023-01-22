package com.sit_hisazumi_android_app_dev.entity

import java.util.*

@kotlinx.serialization.Serializable
data class Task(var id:String = UUID.randomUUID().toString(), val title:String, val date:Long)