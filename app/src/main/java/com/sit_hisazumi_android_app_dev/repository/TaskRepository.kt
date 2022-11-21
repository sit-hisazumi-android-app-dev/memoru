package com.sit_hisazumi_android_app_dev.repository

import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.entity.TaskKind
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

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

    override fun findAll(): Flow<List<Task>> {
        return flow {
            listOf(
                Task("A",null,TaskKind.MEMO),
                Task("B",null,TaskKind.MEMO),
                Task("C",null,TaskKind.MEMO),
                Task("D",null,TaskKind.MEMO)
            )
        }
    }
}

//memo mock
class TodoRepositoryMock: ITaskRepository{
    override fun save(tasks: List<Task>) {

    }

    override fun findAll(): Flow<List<Task>> {
        return flow {
            listOf(
                Task("A",LocalDateTime.of(2022,1,1,1,1),TaskKind.TODO),
                Task("B",LocalDateTime.of(2022,2,1,1,1),TaskKind.TODO),
                Task("C",LocalDateTime.of(2022,4,1,1,1),TaskKind.TODO),
                Task("D",LocalDateTime.of(2022,11,1,1,1),TaskKind.TODO)
            )
        }
    }
}