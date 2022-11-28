package com.sit_hisazumi_android_app_dev.component

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*

@Composable
@ExperimentalMaterialApi
fun Display(){
    var isDisplayTodo by remember{
        mutableStateOf(true)
    }

    if(isDisplayTodo){
        TodoList()
    }else{
        MemoList()
    }

}