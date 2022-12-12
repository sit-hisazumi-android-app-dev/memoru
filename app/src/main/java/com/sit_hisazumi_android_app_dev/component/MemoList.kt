package com.sit_hisazumi_android_app_dev.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.repository.ITaskRepository
import com.sit_hisazumi_android_app_dev.repository.MemoRepositoryMock
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
@Composable
fun MemoList(dataSource: ITaskRepository){
    val list = dataSource.findAll().collectAsState(initial = listOf())

    LazyColumn {
        items(list.value){ memo ->
            TODODisplay(repository = dataSource, item = memo)
        }
    }
}