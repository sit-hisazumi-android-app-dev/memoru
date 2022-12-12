package com.sit_hisazumi_android_app_dev.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.repository.ITaskRepository
import com.sit_hisazumi_android_app_dev.repository.TodoRepositoryMock
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@Composable
fun TodoList(dataSource: ITaskRepository){

    val list = dataSource.findAll().collectAsState(initial = listOf())

    LazyColumn {
        items(list.value){ todo ->
            TODODisplay(repository = dataSource, item = todo)
        }
    }
}