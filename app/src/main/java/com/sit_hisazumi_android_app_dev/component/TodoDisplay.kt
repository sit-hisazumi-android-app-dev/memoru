package com.sit_hisazumi_android_app_dev.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sit_hisazumi_android_app_dev.entity.Task
import com.sit_hisazumi_android_app_dev.repository.ITaskRepository
import kotlinx.coroutines.flow.map
import java.time.format.DateTimeFormatter

@ExperimentalMaterialApi
@Composable
fun TODODisplay(repository: ITaskRepository, item: Task){

    val dismissState = rememberDismissState(
        confirmStateChange = {
        if ( it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
            repository.findAll().map { repo ->
                repository.save(repo.filter { list -> list.id !=  item.id})
            }
        }
        true
    })
    
    SwipeToDismiss(state = dismissState,
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    DismissValue.DismissedToEnd -> Color.Red
                    else -> Color.LightGray
                }
            )

            val icon = when(direction){
                DismissDirection.EndToStart -> "削除"
                else -> {""}
            }

            val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)

            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color)
                .padding(start = 12.dp, end = 12.dp)){
                Text(modifier = Modifier.scale(scale), text = icon)
            }
        }){
        Box(modifier =
        Modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(10)
            )
            .padding(5.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        ) {
            item.date
            Column(
                Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10)
                    )
                    .fillMaxWidth()
            ) {
                Text(text = item.title)
                Spacer(Modifier.size(16.dp))
                item.date?.format(DateTimeFormatter.ISO_DATE_TIME)?.let { Text(text = it) }
            }
        }
    }

}
