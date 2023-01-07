package com.sit_hisazumi_android_app_dev.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun AddTaskForm(modifier: Modifier = Modifier){
    var text by remember { mutableStateOf("") }
    val themeColor = Color(0, 117, 255)
    Box (
        Modifier
            .background(color = themeColor)
            .fillMaxWidth()
            ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl,
        ){
            Row(
                modifier =
                modifier
                    .padding(top = 4.dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
                    .background(color = themeColor),
            ) {
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
                Surface(
                    color = themeColor
                ) {
                    CompositionLocalProvider(
                        LocalLayoutDirection provides LayoutDirection.Ltr,
                    ){
                        TextField(
                            shape = RoundedCornerShape(5.dp),
                            modifier =
                            modifier
                                .padding(start = 4.dp,end = 4.dp)
                                .fillMaxWidth(),
                            value = text,
                            onValueChange = { text = it },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}
