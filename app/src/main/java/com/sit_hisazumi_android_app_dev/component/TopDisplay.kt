package com.sit_hisazumi_android_app_dev.component

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import com.sit_hisazumi_android_app_dev.repository.MemoRepository
import com.sit_hisazumi_android_app_dev.repository.MemoRepositoryMock
import com.sit_hisazumi_android_app_dev.repository.TodoRepository
import com.sit_hisazumi_android_app_dev.repository.TodoRepositoryMock
import androidx.datastore.preferences.createDataStore

@Composable
@ExperimentalMaterialApi
fun Display(dataStore: DataStore<Preferences>){

    var tabIndex by remember{
        mutableStateOf(0)
    }

    val todoSource = TodoRepository(dataStore)
    val memoSource = MemoRepository(dataStore)

    Column(modifier = Modifier.fillMaxHeight()) {
        ChangeTab(tabIndex = tabIndex, onTabIndexChange = {tabIndex = it})
        if(tabIndex == 0){
            TodoList(todoSource)
        }else{
            MemoList(memoSource)
        }
    }

}