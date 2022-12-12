package com.sit_hisazumi_android_app_dev.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.sit_hisazumi_android_app_dev.repository.MemoRepositoryMock
import com.sit_hisazumi_android_app_dev.repository.TodoRepositoryMock

@Composable
@ExperimentalMaterialApi
fun Display(){

    var tabIndex by remember{
        mutableStateOf(0)
    }

    val todoSource = TodoRepositoryMock()
    val memoSource = MemoRepositoryMock()

    Column(modifier = Modifier.fillMaxHeight()) {
        ChangeTab(tabIndex = tabIndex, onTabIndexChange = {tabIndex = it})
        if(tabIndex == 0){
            TodoList(todoSource)
        }else{
            MemoList(memoSource)
        }
    }

}