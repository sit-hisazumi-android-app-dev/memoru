package com.sit_hisazumi_android_app_dev.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.repository.*

@Composable
@ExperimentalMaterialApi
fun Display(){

    var tabIndex by remember{
        mutableStateOf(0)
    }

    val context = LocalContext.current

    val todoSource = TodoRepository(context.dataStore)
    val memoSource = MemoRepository(context.dataStore)

    val todoList = mutableListOf<Task>()
    var memoList = mutableListOf<Task>()

    val reload:@Composable () -> Unit = {
        todoList.clear()
        memoList.clear()
        todoSource.findAll().collectAsState(initial = listOf()).value.forEach{t->
            todoList.add(t)
        }
        memoSource.findAll().collectAsState(initial = listOf()).value.forEach{t->
            memoList.add(t)
        }
    }

    todoList.addAll(todoSource.findAll().collectAsState(initial = listOf()).value)
    memoList.addAll(memoSource.findAll().collectAsState(initial = listOf()).value)

    Column {
        Box(contentAlignment = Alignment.BottomCenter){
            Column(modifier = Modifier.fillMaxHeight()) {
                ChangeTab(tabIndex = tabIndex, onTabIndexChange = {tabIndex = it})
                if(tabIndex == 0){
                    TodoList(todoList,todoSource,reload)
                }else{
                    MemoList(memoList,memoSource,reload)
                }
            }
            if(tabIndex == 1){
                AddTaskForm(isMemo = true, list = memoList, repository = memoSource)
            }else{
                AddTaskForm(isMemo = false, list = todoList, repository = todoSource)
            }
        }
    }

}