package com.example.libraandroid.ui.payment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R

@Composable
fun ExpandableSurface(
    title: String,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val surfaceStartPadding = 16.dp
    val surfaceEndPadding = 16.dp
    val surfaceTopPadding = 12.dp
    val surfaceBottomPadding = 8.dp

    val verticalSpace = 8.dp

    Surface(
        modifier = modifier
            .wrapContentSize(),
        border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = surfaceStartPadding,
                    end = surfaceEndPadding,
                    top = surfaceTopPadding,
                    bottom = surfaceBottomPadding
                ),
        ) {
            TitleOnExpandableSurface(title = title, onClick = onExpand)
            
            Spacer(modifier = Modifier.height(verticalSpace))
            content()
        }
    }
}

@Composable
fun TitleOnExpandableSurface(
    title: String,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            title,
            style = MaterialTheme.typography.h6
        )

        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = stringResource(R.string.scr_balance__icon__expand_balance),
            modifier = Modifier
                .size(
                    dimensionResource(R.dimen.scr_pay__surface__top_right_icon)
                )
                .clickable {
                    onClick()
                }
        )
    }
}

@Preview
@Composable
fun PreviewTitleOnExpandableSurface() {
    TitleOnExpandableSurface(title = "Balance") {}
}

@Preview
@Composable
fun PreviewExpandableSurface() {
    ExpandableSurface(title = "Balance", onExpand = {}) {

    }
}