package com.sit_hisazumi_android_app_dev.repository

import com.sit_hisazumi_android_app_dev.entity.Task

interface ITaskRepository {
    fun save(tasks: List<Task>)
    fun findAll() :List<Task>
}