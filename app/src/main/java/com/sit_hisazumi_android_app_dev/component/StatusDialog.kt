package com.sit_hisazumi_android_app_dev.component

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

@Composable
fun AlertDialogSample() {
    val (time,setTime) = remember{ mutableStateOf(Date().time) }
    val c = Calendar.getInstance()
    val year = c[Calendar.YEAR]
    val month = c[Calendar.MONTH]
    val day = c[Calendar.DAY_OF_MONTH]

    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            setTime(LocalDate.of(year, month + 1, dayOfMonth).atStartOfDay().toEpochSecond(ZoneOffset.of(ZoneOffset.systemDefault().id)))
            //onValueChange(LocalDate.of(year, month + 1, dayOfMonth).toString())
        },
        year,
        month,
        day,
    )
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(false)  }

            Button(
                onClick = {
                openDialog.value = true
            }) {
                Text("Click me", textAlign = TextAlign.Center)
            }
            Text(time.toString())

            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Box(modifier = Modifier.clickable {
                            dialog.show()
                        }) {
                            Text(
                                text = "日時：" +
                                        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPAN)
                                            .format(Date(time))
                            )
                        }
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    dismissButton = {
                        Button(modifier = Modifier.fillMaxWidth(),

                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("キャンセル", textAlign = TextAlign.Center)
                        }
                    },
                    confirmButton = {
                        Button(modifier = Modifier.fillMaxWidth(),

                            onClick = {
                                openDialog.value = false
                            }) {
                            Text("+", textAlign = TextAlign.Center)
                        }
                    }
                )
            }
        }

    }
}
//確認用
@Preview
@Composable
fun PreviewResult(){
    AlertDialogSample();
}