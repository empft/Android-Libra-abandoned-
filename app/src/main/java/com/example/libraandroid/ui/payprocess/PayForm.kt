package com.example.libraandroid.ui.payprocess

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraandroid.R
import com.example.libraandroid.ui.currency.OutlinedTextFieldAmount
import com.example.libraandroid.ui.currency.formatAmount
import com.example.libraandroid.ui.dropdownmenu.OutlinedTextFieldDropDown
import com.example.libraandroid.ui.textfield.PasteIconButton
import java.math.BigInteger

@Composable
fun PayForm(
    wallets: List<String>,
    currencies: List<String>,
    modifier: Modifier = Modifier,
    celoGatewayState: CeloGatewayState? = null
) {
    var selectedWallet: Int? by remember { mutableStateOf(null) }
    var selectedCurrency: Int? by remember { mutableStateOf(null) }
    var recipient by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        OutlinedTextFieldDropDown(
            values = wallets,
            selected = selectedWallet,
            onSelect = {
                selectedWallet = it
            },
            label = {
                Text(
                    stringResource(R.string.scr_payprocess__textfield__wallet)
                )
            }
        )
        
        OutlinedTextFieldDropDown(
            values = currencies,
            selected = selectedCurrency, 
            onSelect = {
                selectedCurrency = it
            },
            label = {
                Text(stringResource(R.string.scr_payprocess__textfield__currency))
            }
        )
        
        OutlinedTextField(
            value = recipient, onValueChange = { recipient = it },
            label = {
                Text(stringResource(R.string.scr_payprocess__textfield__recipient))
            },
            trailingIcon = {
                PasteIconButton(onPaste = { recipient = it })
            }
        )

        celoGatewayState?.let {
            Spacer(modifier = Modifier.height(48.dp))
            
            CeloPayFormSection(it)
        }
    }
}

@Composable
fun CeloPayFormSection(
    celoGatewayState: CeloGatewayState
) {
    OutlinedTextFieldDropDown(
        values = celoGatewayState.recipients,
        selected = celoGatewayState.selectedRecipient,
        onSelect = celoGatewayState::onSelectRecipient,
        label = {
            Text(stringResource(R.string.scr_payprocess__textfield__gateway_recipient))
        }
    )

    OutlinedTextFieldDropDown(
        values = celoGatewayState.currencies,
        selected = celoGatewayState.selectedCurrency,
        onSelect = celoGatewayState::onSelectCurrency,
        label = {
            Text(stringResource(R.string.scr_payprocess__textfield__gateway_currency))
        }
    )

    OutlinedTextFieldAmount(
        value = celoGatewayState.fee,
        onValueChange = celoGatewayState::onEditFee,
        decimalPlaces = celoGatewayState.decimalPlaces,
        label = {
            Text(stringResource(R.string.scr_payprocess__textfield__gateway_fee))
        }
    )
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewPayForm() {
    PayForm(
        listOf(), listOf(), Modifier,
        rememberCeloGatewayState(
            recipients = listOf(),
            currencies = listOf(),
            selectedRecipient = null,
            selectedCurrency = null,
            fee = BigInteger.TEN,
            decimalPlaces = 1
        )
    )
}