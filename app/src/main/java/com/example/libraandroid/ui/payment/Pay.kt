package com.example.libraandroid.ui.payment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R

@Composable
fun PayButton(
    onClick: () -> Unit,
    text: String,
    icon: Painter,
    modifier: Modifier = Modifier,
) {
    val iconSize = 80.dp

    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            (1.5).dp, brush = Brush.sweepGradient(
                colors = listOf(
                    MaterialTheme.colors.primary,
                    MaterialTheme.colors.secondary,
                )
            ))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = icon,
                contentDescription = text,
                modifier = Modifier.size(iconSize)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text,
                modifier = Modifier.widthIn(
                    max = iconSize
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Preview
@Composable
fun PreviewPayButton() {
    PayButton(
        onClick = {},
        text = "QR",
        icon = painterResource(R.drawable.ic_qr)
    )
}

@Composable
fun Pay(
    onClickQr: () -> Unit,
    onClickDirect: () -> Unit,
    onClickNfc: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        PayButton(
            onClick = onClickQr,
            text = stringResource(R.string.scr_pay__btn__qr),
            icon = painterResource(R.drawable.ic_qr)
        )
        PayButton(
            onClick = onClickDirect,
            text = stringResource(R.string.scr_pay__btn__direct_pay),
            icon = painterResource(R.drawable.ic_direct_arrow)
        )
        PayButton(
            onClick = onClickNfc,
            text = stringResource(R.string.scr_pay__btn__nfc),
            icon = painterResource(R.drawable.ic_nfc)
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewPay() {
    Pay(
        {}, {}, {}
    )
}