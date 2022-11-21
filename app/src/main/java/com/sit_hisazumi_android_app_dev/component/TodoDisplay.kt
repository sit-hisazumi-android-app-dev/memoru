package com.sit_hisazumi_android_app_dev.component

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sit_hisazumi_android_app_dev.ui.theme.MemoruTheme


class TodoDisplay : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoruTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}

@Composable
fun ModeChangeSwitch(modifier: Modifier = Modifier){
    var mode by remember { mutableStateOf(1) }
    val TODOJudge = when(mode){
        1 -> "----------------"
        else -> ""
    }
    val MEMOJudge = when(mode){
        2 -> "----------------"
        else -> ""
    }
    //mode1 stands for Task mode, mode2 stands for Memo mode.

    Row(modifier = modifier){
        Column(modifier = modifier) {
            Text(text = "やること")
        }
        Column(modifier = modifier) {
            Text(text = "MEMOooooooo")
            Text(text = "メモ")
        }
    }
}

@Composable
fun ShowTheMode(modifier: Modifier = Modifier){
    Row(
        modifier = modifier,
    ) {
        Text(text = "--------")
    }
}

@Composable
fun TODODisplay(title: String = "Working", date: String = "2022/11/30"){
    Box(modifier = Modifier.background(color = Color.LightGray,
        shape = RoundedCornerShape(10))
        .padding(5.dp)) {
        Column(Modifier.background(color = Color.White,
            shape = RoundedCornerShape(10))) {
            Text(text = title)
            Spacer(Modifier.size(16.dp))
            Text(text = date)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MemoruTheme {
        TODODisplay()
    }
}
