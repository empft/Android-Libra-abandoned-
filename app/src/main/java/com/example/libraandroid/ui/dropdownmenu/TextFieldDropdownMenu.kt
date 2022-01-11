package com.example.libraandroid.ui.dropdownmenu

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity

@Composable
fun OutlinedTextFieldDropDown(
    values: List<String>,
    selected: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
) {
    var dropDownWidth by remember { mutableStateOf(0) }
    val expandedState = remember { mutableStateOf(false) }
    val rotation: Float by animateFloatAsState(if (expandedState.value) 180f else 0f)

    val source = remember {
        MutableInteractionSource()
    }

    TextFieldDropDownComponent(
        expandedState = expandedState,
        sourceState = source,
        dropDownWidth = dropDownWidth,
        values = values,
        onSelect = onSelect,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = values[selected],
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.onSizeChanged {
                dropDownWidth = it.width
            },
            interactionSource = source,
            label = label,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(rotation)
                )
            }
        )
    }
}

@Composable
fun TextFieldDropDown(
    values: List<String>,
    selected: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
) {
    var dropDownWidth by remember { mutableStateOf(0) }
    val expandedState = remember { mutableStateOf(false) }
    val rotation: Float by animateFloatAsState(if (expandedState.value) 180f else 0f)

    val source = remember {
        MutableInteractionSource()
    }

    TextFieldDropDownComponent(
        expandedState = expandedState,
        sourceState = source,
        dropDownWidth = dropDownWidth,
        values = values,
        onSelect = onSelect,
        modifier = modifier
    ) {
        TextField(
            value = values[selected],
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.onSizeChanged {
                dropDownWidth = it.width
            },
            interactionSource = source,
            label = label,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(rotation)
                )
            }
        )
    }
}

@Composable
private fun TextFieldDropDownComponent(
    expandedState: MutableState<Boolean>,
    sourceState: MutableInteractionSource,
    dropDownWidth: Int,
    values: List<String>,
    onSelect: (Int) -> Unit,
    modifier: Modifier,
    textField: @Composable () -> Unit
) {
    if (sourceState.collectIsPressedAsState().value) {
        expandedState.value = !expandedState.value
    }

    Column(modifier = modifier) {
        textField()
        DropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = { expandedState.value = false },
            modifier = Modifier.width(with(LocalDensity.current) {
                dropDownWidth.toDp()
            })
        ) {
            values.forEachIndexed { index, value ->
                DropdownMenuItem(onClick = {
                    onSelect(index)
                    expandedState.value = false
                }) {
                    Text(value)
                }
            }
        }
    }
}