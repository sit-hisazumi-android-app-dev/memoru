package com.sit_hisazumi_android_app_dev.repository

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.entity.TaskKind
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.time.LocalDateTime

//MemoRepository と TodoRepositoryの2種類のレポジトリクラスを用意して、それぞれITaskRepositoryを実装する
//MemoRepositoryのPreferences DataStore Nameは"memo_repository"
//TodoRepositoryのPreferences DataStore Nameは"todo_repository"
interface ITaskRepository {
    suspend fun save(tasks: List<Task>)
    fun findAll() : Flow<List<Task>>
}

class MemoRepository: ITaskRepository {
    override suspend fun save(tasks: List<Task>) {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

}

class TodoRepository: ITaskRepository {
    lateinit var dataStore: DataStore<Preferences>;

    //ここにMainActivity.ktからのcreateDataStoreが入る
    constructor(dataStore: DataStore<Preferences>){
        this.dataStore = dataStore
    }

    //TODOまたはMEMOをキーとする
    private object PreferenceKeys {
        val TaskKindName = preferencesKey<String>("ToDo")
    }

    //jsonContentはjson文字列化されたデータ
    private suspend fun updateName(jsonContent: String) {
        dataStore.edit {
            it[PreferenceKeys.TaskKindName] = jsonContent
        }
    }

    //記録
    override suspend fun save(tasks: List<Task>) {
        var hoge = tasks
        var jsonContent = Json.encodeToString(hoge)

        updateName(jsonContent)
        TODO("Not yet implemented")
    }

    override fun findAll(): Flow<List<Task>> {
        val preferencesFlow: Flow<String> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[PreferenceKeys.TaskKindName] ?: "Not Found"
            }

        TODO("Not yet implemented")
    }

}

//memo mock
class MemoRepositoryMock: ITaskRepository{
    override suspend fun save(tasks: List<Task>) {

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
    lateinit var dataStore: DataStore<Preferences>

    //書き込み
    override suspend fun save(tasks: List<Task>) {
        var hoge = tasks
        var jsonContent = Json.encodeToString(hoge)

    }

    //読み込み＆表示
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