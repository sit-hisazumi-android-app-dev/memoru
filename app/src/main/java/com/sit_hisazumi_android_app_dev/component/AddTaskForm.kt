package com.sit_hisazumi_android_app_dev.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sit_hisazumi_android_app_dev.ui.theme.MemoruTheme

@Composable
fun AddTaskForm(modifier: Modifier = Modifier){
    var text by remember { mutableStateOf("") }
    val themeColor = Color(0, 117, 255)
    Box (
        Modifier
            .background(color = themeColor),
            ) {
        Row(
            modifier =
            modifier
                .padding(top = 4.dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
                .background(color = themeColor),
        ) {
            Surface(
                color = themeColor
            ) {
                TextField(
                    shape = RoundedCornerShape(5.dp),
                    modifier =
                    modifier
                        .padding(end = 4.dp),
                    value = text,
                    onValueChange = { text = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    )
                )
            }
            Box(
                modifier = modifier.padding(top = 7.5.dp)
            ){
                Button( // 5
                    modifier =
                    modifier.height(40.dp),
                    onClick = {
                    },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.White,
                        contentColor = themeColor,
                        disabledContentColor = Color.LightGray
                    ),
                    shape = RectangleShape
                ) {
                    Text(buildAnnotatedString {
                        withStyle(SpanStyle(color = themeColor)){
                            append("+")
                        }
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MemoruTheme {
        AddTaskForm()
    }
}