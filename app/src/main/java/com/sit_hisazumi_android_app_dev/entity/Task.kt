package com.sit_hisazumi_android_app_dev.entity

import java.time.LocalDateTime

enum class TaskKind { TODO,MEMO }

data class Task(val title:String, val date:LocalDateTime?, val kind: TaskKind)
