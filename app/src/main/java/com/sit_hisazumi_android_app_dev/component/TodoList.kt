package com.sit_hisazumi_android_app_dev.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.repository.ITaskRepository
import com.sit_hisazumi_android_app_dev.repository.TodoRepository

@ExperimentalMaterialApi
@Composable
fun TodoList(list: List<Task>,dataSource: ITaskRepository,reload:@Composable () -> Unit){

    val displayList = if(dataSource is TodoRepository) list.subList(0, if(list.size < 5) list.size else 5) else list
    LazyColumn {
        items(displayList,{item:Task -> item.id}){ todo ->
            TODODisplay(repository = dataSource, item = todo, reload = reload)
        }
    }
}