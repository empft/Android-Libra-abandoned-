package com.example.libraandroid.domain.applicationsession

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map

class SessionManager(
    private val repo: SessionRepository
) {
    val currentSession: Flow<ApplicationSession.Type?> = repo.session.map {
        it.current
    }
    val allSessions: Flow<List<ApplicationSession.Type.User>?> = repo.session.map {
        it.savedSessions.toList()
    }

    suspend fun initGuestSession() {
        val session = repo.session.lastOrNull() ?: ApplicationSession()
        session.setGuest()
        repo.set(session)
    }

    suspend fun initSession(user: ApplicationSession.Type.User) {
        val session = repo.session.lastOrNull() ?: ApplicationSession()
        session.addSessionAndSetActive(user)
        repo.set(session)
    }

    suspend fun killActiveSession() {
        repo.session.lastOrNull()?.let {
            it.removeSession()
            repo.set(it)
        }
    }
}