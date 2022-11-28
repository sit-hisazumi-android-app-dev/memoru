package com.sit_hisazumi_android_app_dev.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sit_hisazumi_android_app_dev.ui.theme.MemoruTheme

@Composable
fun CustomTabs() {
    val themeColor = Color(0, 117, 255)
    var tabIndex by remember { mutableStateOf(0) } // 1.
    val tabTitles = listOf("TODO","MEMO")
    Column { // 2.
        TabRow(
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .height(3.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .background(themeColor)
                )
            },
            selectedTabIndex = tabIndex,
            backgroundColor = Color.White) { // 3.
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = tabIndex == index, // 4.
                    onClick = { tabIndex = index },
                    text = { Text(text = title,color = (if (index == tabIndex) themeColor else Color.Black)) }) // 5.
            }
        }
    }
}