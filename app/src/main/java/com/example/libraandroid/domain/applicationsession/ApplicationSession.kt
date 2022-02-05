package com.example.libraandroid.domain.applicationsession

data class ApplicationSession(
    val savedSessions: MutableList<Type.User> = mutableListOf(),
    var _current: Int? = null
) {
    // Stores a list of account session and an index
    // the index indicates which session is active, negative index is used for guest session
    // null index means no active session

    val current: Type?
        get() {
            _current?.let {
                if (it < 0) {
                    return Type.Guest
                }
                return savedSessions.elementAt(it)
            }
            return null
        }

    sealed interface Type {
        data class User(
            val savedAccountSession: SavedAccountSession
        ): Type {
            data class SavedAccountSession(
                val token: String,
                val name: String
            )
        }

        object Guest: Type
    }

    fun addSession(user: Type.User) {
        savedSessions.add(user)
    }

    fun addSessionAndSetActive(user: Type.User) {
        addSession(user)
        setActive(savedSessions.lastIndex)
    }

    fun setActive(index: Int) {
        _current = index
    }

    fun setGuest() {
        _current = -1
    }

    fun removeSession() {
        _current = null
    }
}



