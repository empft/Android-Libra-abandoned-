package com.example.libraandroid.domain.finance.database

import androidx.room.*
import com.example.libraandroid.domain.finance.chain.ChainMainNet
import com.example.libraandroid.domain.finance.chain.ChainName

import kotlinx.coroutines.flow.Flow

@Dao
interface CeloBalanceDao {
    @Query(
        "SELECT * FROM CeloCurrency"
    )
    fun currencies(): List<CeloCurrencyDto>

    @Query(
        "SELECT * FROM CeloCurrency " +
                "WHERE CeloCurrency.ChainId = :chainId " +
                "AND CeloCurrency.ChainName = :chainName"
    )
    fun currenciesInChain(chainId: Int, chainName: String = ChainName.Celo.name): List<CeloCurrencyDto>
    fun currenciesInMainNet() = currenciesInChain(ChainMainNet.Celo.id)

    /*
     Returned Data structure is a bit weird but it can be converted to required form easily by iteration.
     */

    @Query(
        "SELECT * FROM CeloBalance " +
                "INNER JOIN CeloCurrency ON CeloBalance.CurrencyAddress = CeloCurrency.Currency " +
                "AND CeloBalance.ChainName = CeloCurrency.ChainName " +
                "AND CeloBalance.ChainId = CeloCurrency.ChainId " +
                "WHERE CeloCurrency.IsFocused = :isFocused AND " +
                "(EXISTS (SELECT 1 FROM DeviceWallet WHERE " +
                "CeloBalance.WalletAddress = DeviceWallet.Address AND " +
                "CeloBalance.ChainId = DeviceWallet.ChainName AND " +
                "CeloBalance.ChainName = DeviceWallet.ChainName))"
    )
    fun focusedBalancesInDeviceWallets(isFocused: Boolean = true): Flow<Map<CeloCurrencyDto, List<CeloBalanceDto>>>

    @Query(
        "SELECT * FROM CeloBalance " +
                "INNER JOIN CeloCurrency ON CeloBalance.CurrencyAddress = CeloCurrency.Currency " +
                "AND CeloBalance.ChainName = CeloCurrency.ChainName " +
                "AND CeloBalance.ChainId = CeloCurrency.ChainId " +
                "WHERE EXISTS (SELECT 1 FROM DeviceWallet WHERE " +
                "CeloBalance.WalletAddress = DeviceWallet.Address AND " +
                "CeloBalance.ChainId = DeviceWallet.ChainName AND " +
                "CeloBalance.ChainName = DeviceWallet.ChainName)"
    )
    fun balancesInDeviceWallets(): Flow<Map<CeloCurrencyDto, List<CeloBalanceDto>>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrency(vararg celoCurrency: CeloCurrencyDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBalance(vararg celoBalance: CeloBalanceDto)

    @Transaction
    fun insertBalancesWithCurrency(currency: CeloCurrencyDto, vararg balance: CeloBalanceDto) {
        insertCurrency(currency)
        insertBalance(*balance)
    }

    @Update
    fun updateCurrency(vararg celoCurrency: CeloCurrencyDto)

    @Update
    fun updateBalance(vararg celoBalance: CeloBalanceDto)
}