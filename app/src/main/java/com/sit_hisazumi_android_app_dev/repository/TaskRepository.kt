package com.sit_hisazumi_android_app_dev.repository

import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.entity.TaskKind
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.*

//MemoRepository と TodoRepositoryの2種類のレポジトリクラスを用意して、それぞれITaskRepositoryを実装する
//MemoRepositoryのPreferences DataStore Nameは"memo_repository"
//TodoRepositoryのPreferences DataStore Nameは"todo_repository"
interface ITaskRepository {
    fun save(tasks: List<Task>)
    fun findAll() : Flow<List<Task>>
}

//memo mock
class MemoRepositoryMock: ITaskRepository{

    override fun save(tasks: List<Task>) {

    }

    fun getList(): List<Task> {
        return listOf(
            Task(UUID.randomUUID().toString(),"A",null,TaskKind.MEMO),
            Task(UUID.randomUUID().toString(),"B",null,TaskKind.MEMO),
            Task(UUID.randomUUID().toString(),"C",null,TaskKind.MEMO),
            Task(UUID.randomUUID().toString(),"D",null,TaskKind.MEMO)
        )
    }

    override fun findAll(): Flow<List<Task>> {
        return listOf(getList()).asFlow()
    }
}

//memo mock
class TodoRepositoryMock: ITaskRepository{
    override fun save(tasks: List<Task>) {

    }

    override fun findAll(): Flow<List<Task>> {
        return listOf(listOf(
            Task(UUID.randomUUID().toString(),"A",LocalDateTime.of(2022,1,1,1,1),TaskKind.TODO),
            Task(UUID.randomUUID().toString(),"B",LocalDateTime.of(2022,2,1,1,1),TaskKind.TODO),
            Task(UUID.randomUUID().toString(),"C",LocalDateTime.of(2022,4,1,1,1),TaskKind.TODO),
            Task(UUID.randomUUID().toString(),"D",LocalDateTime.of(2022,11,1,1,1),TaskKind.TODO)
        )).asFlow()
    }
}