package com.example.libraandroid.room

import androidx.room.TypeConverter
import java.math.BigInteger

object BigIntegerTypeConverter {
    @TypeConverter
    fun stringToBigInteger(value: String?): BigInteger? {
        return if (value != null) {
            BigInteger(value)
        } else {
            null
        }
    }

    @TypeConverter
    fun bigIntegerToString(value: BigInteger?): String? {
        return value?.toString()
    }
}