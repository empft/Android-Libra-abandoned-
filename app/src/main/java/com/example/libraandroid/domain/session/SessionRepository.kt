package com.example.libraandroid.domain.session

interface SessionRepository<T> {
    fun getAll(): Pair<MutableList<T>?, Int?>
    fun setAll(values: List<T>?, index: Int?)

    fun get(): T?
    fun set(value: T)
    fun exist(): Boolean
    fun delete()
}