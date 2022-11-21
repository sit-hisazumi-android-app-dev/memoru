package com.sit_hisazumi_android_app_dev.repository

import com.sit_hisazumi_android_app_dev.entity.Task
import kotlinx.coroutines.flow.Flow

//MemoRepository と TodoRepositoryの2種類のレポジトリクラスを用意して、それぞれITaskRepositoryを実装する
interface ITaskRepository {
    fun save(tasks: List<Task>)
    fun findAll() : Flow<List<Task>>
}