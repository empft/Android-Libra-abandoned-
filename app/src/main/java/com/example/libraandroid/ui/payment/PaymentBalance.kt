package com.example.libraandroid.ui.payment

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R

@Composable
fun PaymentBalance(
    amounts: List<String>,
    onClickConvert: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExpandableSurface(title = stringResource(
        R.string.scr_pay__text__balance
    ), onExpand = onNavigate, modifier = modifier) {
        amounts.forEach {
            Text(
                text = it,
                style = MaterialTheme.typography.h5
            )
        }

        TextButton(onClick = onClickConvert,
            contentPadding = PaddingValues(start = 0.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_convert),
                contentDescription = stringResource(
                    R.string.scr_balance__btn__add_convert_money
                ),
                modifier = Modifier.size(
                    dimensionResource(R.dimen.g__textbtn__icon)
                )
            )
            Spacer(Modifier.width(
                dimensionResource(R.dimen.g__textbtn__icon_text_spacing)
            ))
            Text(
                stringResource(
                    R.string.scr_balance__btn__add_convert_money
                ),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewBalance() {
    PaymentBalance(
        listOf("$20.00", "30$30", "c$100"),
        {}, {}
    )
}