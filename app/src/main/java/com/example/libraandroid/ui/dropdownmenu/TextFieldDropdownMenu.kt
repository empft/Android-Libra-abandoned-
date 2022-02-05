package com.example.libraandroid.ui.dropdownmenu

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
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
    selected: Int?,
    onSelect: (Int) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dropdownContent: @Composable (Int) -> Unit = { Text(values[it]) },
) {
    var dropdownWidth by remember { mutableStateOf(0) }
    val expandedState = remember { mutableStateOf(false) }
    val rotation: Float by animateFloatAsState(if (expandedState.value) 180f else 0f)

    val source = remember {
        MutableInteractionSource()
    }

    TextFieldDropDownComponent(
        expandedState = expandedState,
        sourceState = source,
        dropdownWidth = dropdownWidth,
        listLength = values.count(),
        onSelect = onSelect,
        modifier = modifier,
        textField = {
            OutlinedTextField(
                value = if (selected != null) values[selected] else "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.onSizeChanged {
                    dropdownWidth = it.width
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
    ) {
        dropdownContent(it)
    }
}

@Composable
fun TextFieldDropDown(
    values: List<String>,
    selected: Int?,
    onSelect: (Int) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dropdownContent: @Composable (Int) -> Unit = { Text(values[it]) },
) {
    var dropdownWidth by remember { mutableStateOf(0) }
    val expandedState = remember { mutableStateOf(false) }
    val rotation: Float by animateFloatAsState(if (expandedState.value) 180f else 0f)

    val source = remember {
        MutableInteractionSource()
    }

    TextFieldDropDownComponent(
        expandedState = expandedState,
        sourceState = source,
        dropdownWidth = dropdownWidth,
        listLength = values.count(),
        onSelect = onSelect,
        modifier = modifier,
        textField = {
            TextField(
                value = if (selected != null) values[selected] else "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.onSizeChanged {
                    dropdownWidth = it.width
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
    ) {
        dropdownContent(it)
    }
}

@Composable
private fun TextFieldDropDownComponent(
    expandedState: MutableState<Boolean>,
    sourceState: MutableInteractionSource,
    dropdownWidth: Int,
    listLength: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier,
    textField: @Composable () -> Unit,
    dropDownMenuItemContent: @Composable (Int) -> Unit
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
                dropdownWidth.toDp()
            })
        ) {
            for (index in 0 until listLength) {
                DropdownMenuItem(onClick = {
                    onSelect(index)
                    expandedState.value = false
                }) {
                    dropDownMenuItemContent(index)
                }
            }
        }
    }
}