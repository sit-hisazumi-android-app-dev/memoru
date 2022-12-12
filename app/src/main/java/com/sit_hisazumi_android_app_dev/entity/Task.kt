package com.sit_hisazumi_android_app_dev.entity

import java.time.LocalDateTime

enum class TaskKind { TODO,MEMO }

@kotlinx.serialization.Serializable
data class Task(val title:String, val date:LocalDateTime?, val kind: TaskKind)
