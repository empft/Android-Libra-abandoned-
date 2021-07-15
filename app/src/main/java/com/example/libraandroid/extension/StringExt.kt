package com.example.libraandroid.extension

/**
 * Checks whether the string contains at least 1 '@' character.
 * It is impossible to validate all possible email address as defined in RFCs.
 */
fun CharSequence.isEmail(): Boolean {
    return this.contains("@")
}