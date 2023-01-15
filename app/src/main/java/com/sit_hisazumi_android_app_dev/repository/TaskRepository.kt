package com.sit_hisazumi_android_app_dev.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.repository.MemoRepository.PreferenceKeys.TaskKindName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.util.*

//MemoRepository と TodoRepositoryの2種類のレポジトリクラスを用意して、それぞれITaskRepositoryを実装する
//MemoRepositoryのPreferences DataStore Nameは"MEMO"
//TodoRepositoryのPreferences DataStore Nameは"TODO"
interface ITaskRepository {
    suspend fun save(tasks: List<Task>)
    fun findAll() : Flow<List<Task>>
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "stored_data")

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
                Json.decodeFromString(preferences[TaskKindName] ?: "[]")
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
                Json.decodeFromString(preferences[PreferenceKeys.TaskKindName] ?: "[]")
            }
    }

}

//memo mock
class MemoRepositoryMock: ITaskRepository{
    override suspend fun save(tasks: List<Task>) {

    }

    fun getList(): List<Task> {
        return listOf(
            Task(UUID.randomUUID().toString(),"A",0),
            Task(UUID.randomUUID().toString(),"B",0),
            Task(UUID.randomUUID().toString(),"C",0),
            Task(UUID.randomUUID().toString(),"D",0)
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
            Task(UUID.randomUUID().toString(),"A",0),
            Task(UUID.randomUUID().toString(),"B",0),
            Task(UUID.randomUUID().toString(),"C",0),
            Task(UUID.randomUUID().toString(),"D",0)
        )).asFlow()
    }
}