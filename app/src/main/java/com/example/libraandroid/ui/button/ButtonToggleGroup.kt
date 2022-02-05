package com.example.libraandroid.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

private enum class ToggleGroupData {
    Zero,
    One,
    Two
}

data class ButtonToggleGroupContent(
    val onClick: () -> Unit,
    val content: @Composable () -> Unit
)

@Composable
fun OutlineButtonToggleGroup(
    selected: Int,
    vararg contents: ButtonToggleGroupContent
) {
    val cornerRadius = 8.dp
    val borderWidth = 1.dp

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        contents.forEachIndexed { index, (onClick, content) ->
            OutlinedButton(
                onClick = onClick,
                shape = when (index) {
                    // left outer button
                    0 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = 0.dp,
                        bottomStart = cornerRadius,
                        bottomEnd = 0.dp
                    )
                    // right outer button
                    contents.size - 1 -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = cornerRadius,
                        bottomStart = 0.dp,
                        bottomEnd = cornerRadius
                    )
                    // middle button
                    else -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                },
                border = BorderStroke(
                    borderWidth, if (selected == index) {
                        MaterialTheme.colors.primary
                    } else {
                        Color.DarkGray.copy(alpha = 0.75f)
                    }
                ),
                colors = if (selected == index) {
                    // selected colors
                    ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colors.primary.copy(
                            alpha = 0.1f
                        ), contentColor = MaterialTheme.colors.primary
                    )
                } else {
                    // not selected colors
                    ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.primary
                    )
                },
                modifier = Modifier
                    .offset((-borderWidth * index), 0.dp)
                    .zIndex(if (selected == index) 1f else 0f)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides if (selected == index) MaterialTheme.colors.primary else Color.DarkGray.copy(
                        alpha = 0.9f
                    )
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
inline fun <reified T: Enum<T>> OutlineButtonToggleGroup(
    selected: T,
    crossinline onClick: (T) -> Unit,
    crossinline content: @Composable (T) -> Unit,
) {
    val items = enumValues<T>()

    OutlineButtonToggleGroup(
        selected = items.indexOf(selected)
        , contents = items.map {
            ButtonToggleGroupContent(
                onClick = {
                    onClick(it)
                }, content = {
                    content(it)
                }
            )
        }.toTypedArray()
    )
}

@Preview
@Composable
fun PreviewButtonToggleGroup() {
    val selected = remember {
        mutableStateOf(ToggleGroupData.Zero)
    }

    OutlineButtonToggleGroup(
        selected = selected.component1(), onClick = selected.component2()) {
        when(it) {
            ToggleGroupData.Zero -> {
                Text("Zero")
            }
            ToggleGroupData.One -> {
                Text("One")
            }
            ToggleGroupData.Two -> {
                Text("Two")
            }
        }
    }
}