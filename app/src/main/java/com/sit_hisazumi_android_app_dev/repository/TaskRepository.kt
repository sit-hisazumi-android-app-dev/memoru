package com.sit_hisazumi_android_app_dev.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.entity.TaskKind
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.time.LocalDateTime
import java.util.*

//MemoRepository と TodoRepositoryの2種類のレポジトリクラスを用意して、それぞれITaskRepositoryを実装する
//MemoRepositoryのPreferences DataStore Nameは"MEMO"
//TodoRepositoryのPreferences DataStore Nameは"TODO"
interface ITaskRepository {
    suspend fun save(tasks: List<Task>)
    fun findAll() : Flow<List<Task>>
}

class MemoRepository: ITaskRepository {
    lateinit var dataStore: DataStore<Preferences>;

    //ここにMainActivity.ktからのcreateDataStoreが入る
    constructor(dataStore: DataStore<Preferences>){
        this.dataStore = dataStore
    }

    //MEMOをキーとする
    private object PreferenceKeys {
        val TaskKindName = stringPreferencesKey("MEMO")
    }

    //jsonContentはjson文字列化されたデータ
    private suspend fun updateName(jsonContent: String) {
        dataStore.edit {
            it[PreferenceKeys.TaskKindName] = jsonContent
        }
    }

    //MEMOの場合は日付がnullとなるので、それを取り除いてjson文字列にする。
    override suspend fun save(tasks: List<Task>) {
        val format = Json {encodeDefaults = false}
        var jsonContent = format.encodeToString(tasks)
        updateName(jsonContent)
    }

    override fun findAll(): Flow<List<Task>> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                Json.decodeFromString<List<Task>>(preferences[MemoRepository.PreferenceKeys.TaskKindName] ?: "Not Found")
            }
    }

}

//ToDoの内容を保存
class TodoRepository: ITaskRepository {
    lateinit var dataStore: DataStore<Preferences>;

    //ここにMainActivity.ktからのcreateDataStoreが入る
    constructor(dataStore: DataStore<Preferences>){
        this.dataStore = dataStore
    }

    //TODOをキーとする
    private object PreferenceKeys {
        val TaskKindName = stringPreferencesKey("TODO")
    }

    //jsonContentはjson文字列化されたデータ
    private suspend fun updateName(jsonContent: String) {
        dataStore.edit {
            it[PreferenceKeys.TaskKindName] = jsonContent
        }
    }

    //json文字列にして保存する。
    override suspend fun save(tasks: List<Task>) {
        var jsonContent = Json.encodeToString(tasks)
        updateName(jsonContent)
    }

    override fun findAll(): Flow<List<Task>> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                Json.decodeFromString<List<Task>>(preferences[PreferenceKeys.TaskKindName] ?: "Not Found")
            }
    }

}

//memo mock
class MemoRepositoryMock: ITaskRepository{
    override suspend fun save(tasks: List<Task>) {

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
    lateinit var dataStore: DataStore<Preferences>

    //書き込み
    override suspend fun save(tasks: List<Task>) {
        var jsonContent = Json.encodeToString(tasks)

    }

    //読み込み
    override fun findAll(): Flow<List<Task>> {
        return listOf(listOf(
            Task(UUID.randomUUID().toString(),"A",LocalDateTime.of(2022,1,1,1,1),TaskKind.TODO),
            Task(UUID.randomUUID().toString(),"B",LocalDateTime.of(2022,2,1,1,1),TaskKind.TODO),
            Task(UUID.randomUUID().toString(),"C",LocalDateTime.of(2022,4,1,1,1),TaskKind.TODO),
            Task(UUID.randomUUID().toString(),"D",LocalDateTime.of(2022,11,1,1,1),TaskKind.TODO)
        )).asFlow()
    }
}