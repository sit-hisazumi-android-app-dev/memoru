package com.sit_hisazumi_android_app_dev.entity

import java.time.LocalDateTime
import java.util.*

enum class TaskKind { TODO,MEMO }

data class Task(var id:String = UUID.randomUUID().toString(), val title:String, val date:LocalDateTime?, val kind: TaskKind)
