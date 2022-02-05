package com.example.libraandroid.domain.applicationsession

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.libraandroid.datastore.DefaultDataStoreCipher
import com.example.libraandroid.datastore.JsonSerializer
import com.example.libraandroid.datastore.withEncryption
import com.example.libraandroid.keystore.KeyAlias
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

private val Context.dataStore: DataStore<SavedSession> by dataStore(
    "",
    JsonSerializer(SavedSession.serializer()).withEncryption(
        DefaultDataStoreCipher(
            KeyAlias.ApplicationSession
        )
    )
)

class SessionRepository(private val context: Context) {
    val session: Flow<ApplicationSession> = context.dataStore.data.map {
        it.toApplicationSession()
    }

    suspend fun set(value: ApplicationSession) {
        context.dataStore.updateData {
            SavedSession.from(value)
        }
    }
}

@Serializable
private data class SavedSession(
    val sessions: List<SavedAccountSession>,
    val active: Int?
) {
    @Serializable
    data class SavedAccountSession(
        val name: String,
        val token: String
    )

    fun toApplicationSession(): ApplicationSession {
        return ApplicationSession(
            savedSessions = this.sessions.map {
                ApplicationSession.Type.User(
                    savedAccountSession = ApplicationSession.Type.User.SavedAccountSession(it.token, it.name)
                )
            }.toMutableList(),
           _current = this.active
        )
    }

    companion object {
        fun from(applicationSession: ApplicationSession): SavedSession {
            val sessions = applicationSession.savedSessions.map {
                SavedAccountSession(it.savedAccountSession.name, it.savedAccountSession.token)
            }

            return SavedSession(
                sessions = sessions,
                active = applicationSession._current
            )
        }
    }
}
