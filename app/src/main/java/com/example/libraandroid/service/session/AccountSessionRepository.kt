package com.example.libraandroid.service.session

import android.content.Context
import com.example.libraandroid.service.sharedpref.EncryptedSharedPrefConst
import com.example.libraandroid.service.sharedpref.getEncryptedSharedPreference
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

class AccountSessionRepository(context: Context): SessionRepository<SessionToken> {
    private val encryptedSharedPref = getEncryptedSharedPreference(context)

    private fun getIndex(): Int? {
        val index = encryptedSharedPref.getString(EncryptedSharedPrefConst.ACTIVE_ACCOUNT_SESSION_INDEX, null)
        index?.let {
            return it.toInt()
        }
        return null
    }

    private fun setIndex(index: Int?) {
        if (index != null) {
            with(encryptedSharedPref.edit()) {
                putString(EncryptedSharedPrefConst.ACTIVE_ACCOUNT_SESSION_INDEX, index.toString())
                apply()
            }
        } else {
            with(encryptedSharedPref.edit()) {
                remove(EncryptedSharedPrefConst.ACTIVE_ACCOUNT_SESSION_INDEX)
                apply()
            }
        }
    }

    override fun get(): SessionToken? {
        val (all, index) = this.getAll()

        index?.let {
            all?.getOrNull(index)?.let {
                return it
            }
            this.setIndex(null)
        }
        return null
    }

    override fun set(value: SessionToken) {
        val (all, _) = this.getAll()

        val new = all ?: mutableListOf()
        new.add(value)
        this.setAll(new, new.lastIndex)
    }

    override fun exist(): Boolean {
        return this.getIndex() != null
    }

    override fun delete() {
        val (all, index) = this.getAll()

        index?.let {
            all?.getOrNull(index)?.let {
                all.removeAt(index)
                this.setAll(all, null)
            }
            this.setIndex(null)
        }
    }

    override fun getAll(): Pair<MutableList<SessionToken>?, Int?> {
        val all = encryptedSharedPref.getString(EncryptedSharedPrefConst.ACCOUNT_SESSION, null)
        val index = getIndex()

        all?.let {
            val element = Json.parseToJsonElement(it)
            return Pair(Json.decodeFromJsonElement<MutableList<SessionToken>>(element), index)
        }
        return Pair(null, index)
    }

    override fun setAll(values: List<SessionToken>?, index: Int?) {
        if (values != null && index != null && (index < 0 || index >= values.count())) {
            throw IllegalArgumentException("index is out of list boundaries")
        }

        this.setIndex(index)
        with(encryptedSharedPref.edit()) {
            putString(EncryptedSharedPrefConst.ACCOUNT_SESSION, Json.encodeToJsonElement(values).toString())
            apply()
        }
    }
}