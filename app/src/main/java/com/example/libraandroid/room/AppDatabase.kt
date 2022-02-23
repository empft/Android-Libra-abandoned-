package com.example.libraandroid.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.libraandroid.domain.finance.database.CeloBalanceDto
import com.example.libraandroid.domain.finance.database.CeloCurrencyDto
import com.example.libraandroid.domain.finance.database.DiemBalanceDto

@Database(entities = [
    CeloBalanceDto::class,
    CeloCurrencyDto::class,
    DiemBalanceDto::class,
], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun DiemBalanceDao(): DiemBalanceDto
    abstract fun CeloBalanceDao(): CeloBalanceDto

}