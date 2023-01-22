package com.sit_hisazumi_android_app_dev.component

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.repository.ITaskRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

private suspend fun addRecord(before:List<Task>, append:Task, repository: ITaskRepository){
    repository.save(before+append)
}


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MyDialog(text:String,list:List<Task>,repository: ITaskRepository,onDismiss: () -> Unit) {

    val (time,setTime) = remember{ mutableStateOf(Date().time) }
    val c = Calendar.getInstance()
    val _year = c[Calendar.YEAR]
    val _month = c[Calendar.MONTH]
    val _day = c[Calendar.DAY_OF_MONTH]

    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            setTime(
                LocalDateTime.of(year,month+1,dayOfMonth,0,0,0).toEpochSecond(ZoneOffset.ofHours(9))*1000
            )
        },
        _year,
        _month,
        _day,
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.clickable {
                        dialog.show()
                    }){
                        Text(SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).format(Date(time)))
                    }
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.DarkGray,
                            contentColor = Color.White,
                            disabledContentColor = Color.LightGray
                        )
                    ){
                        Text("キャンセル")
                    }
                    Button(
                        onClick = {
                             GlobalScope.async {
                                 addRecord(list,Task(title = text, date = time),repository)
                             }
                            onDismiss()
                        },
                    ){
                        Text("追加")
                    }
                }
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AddTaskForm(isMemo:Boolean,list:List<Task>,repository: ITaskRepository,modifier: Modifier = Modifier){
    var text by remember { mutableStateOf("") }
    val themeColor = Color(0, 117, 255)

    var (showAddDialog,setShowAddDialog) = remember {
        mutableStateOf(false)
    }

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
                            if(!isMemo) {
                                setShowAddDialog(true)
                            }else{
                                GlobalScope.async {
                                    addRecord(list,Task(title = text, date = 0),repository)
                                }
                            }
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
                                .padding(start = 4.dp, end = 4.dp)
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

    if(showAddDialog){
        MyDialog(text,list,repository) {
            setShowAddDialog(false)
        }
    }
}
