package com.example.libraandroid.ui.displayname

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R

//TODO: Add defense against homograph attack?
@Composable
fun DisplayName(
    name: String,
    modifier: Modifier = Modifier,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    style: TextStyle = TextStyle.Default,
    maxLines: Int = 1,
    isSuspicious: Boolean = false,
    isVerified: Boolean = false
) {
    Row(
        modifier = modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            buildAnnotatedString {
                name.forEach {
                    append(it)
                }
            },
            maxLines = maxLines,
            overflow = overflow,
            style = style
        )

        if (isSuspicious) {
            Surface(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                color = Color.LightGray,
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(
                    stringResource(R.string.g__text__suspicious_name),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            }
        }

        if (isVerified) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_verified_user_24),
                contentDescription = stringResource(R.string.g__text__verified_name)
            )
        }
    }
}

@Preview
@Composable
fun PreviewDisplayName() {
    DisplayName(
        name = "X Æ A-12",
        isSuspicious = true,
        isVerified = true,
    )
}