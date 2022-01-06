package com.example.libraandroid.ui.filtersort

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R

@Composable
fun FilterSortRow(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        title()
        content()
    }
}

@Preview
@Composable
fun PreviewFilterSortRow() {
    FilterSortRow(title = {
        Text("Sort By")
    }) {
        Text("test")
    }
}