package com.sit_hisazumi_android_app_dev.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.repository.TodoRepositoryMock
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@Composable
fun TodoList(){
    val todos = remember{
        mutableStateListOf<Task>()
    }

    val mock = TodoRepositoryMock()
    LaunchedEffect(Unit){
        mock.findAll().onEach { it ->
            it.onEach { it ->
                todos.add(it)
            }
        }
    }

    todos.map { it ->
        TODODisplay(repository = mock, item = it)
    }
}