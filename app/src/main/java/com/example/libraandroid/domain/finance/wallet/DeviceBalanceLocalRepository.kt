package com.example.libraandroid.domain.finance.wallet

import com.example.libraandroid.domain.finance.Currency
import com.example.libraandroid.domain.finance.chain.Chain
import com.example.libraandroid.domain.finance.database.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class DeviceBalanceLocalRepository(
    private val celoBalanceDao: CeloBalanceDao,
    private val diemBalanceDao: DiemBalanceDao
) {
    private fun zipBalanceFlow(
        diemBalance: Flow<List<DiemBalanceDto>>,
        celoBalance: Flow<Map<CeloCurrencyDto, List<CeloBalanceDto>>>
    ): Flow<List<Balance>> {
        val flow = diemBalance
            .map { balances ->
            balances.map { balance ->
                balance.toBalance()
            }
        }

        return celoBalance
            .map { deviceBalances ->
            deviceBalances.map { (currency, balances) ->
                balances.map { balance ->
                    balance.toBalance(currency)
                }
            }.flatten()
        }.zip(flow) { i, j ->
            i + j
        }
    }

    fun focusedBalances(): Flow<List<Balance>> {
        return zipBalanceFlow(
            diemBalanceDao.focusedBalancesInDeviceWallets(),
            celoBalanceDao.focusedBalancesInDeviceWallets()
        )
    }

    fun balances(): Flow<List<Balance>> {
        return zipBalanceFlow(
            diemBalanceDao.balancesInDeviceWallets(),
            celoBalanceDao.balancesInDeviceWallets()
        )
    }
}



