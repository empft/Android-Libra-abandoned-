package com.example.libraandroid.miscellaneous

sealed interface Either<out Success, out Failure> {
    data class Success<T>(val value: T): Either<T, Nothing>
    data class Failure<R>(val value: R): Either<Nothing, R>
}

val <S, F> Either<S, F>.isSuccess: Boolean
    get() {
        return when(this) {
            is Either.Failure -> false
            is Either.Success -> true
        }
    }

val <S, F> Either<S, F>.isFailure: Boolean
    get() {
        return this.isSuccess.not()
    }

fun <R, S, F> Either<S, F>.map(
    transform: (value: S) -> R
): Either<R, F> {
    return when(this) {
        is Either.Failure -> {
            Either.Failure(this.value)
        }
        is Either.Success -> {
            Either.Success(transform(this.value))
        }
    }
}

fun <R, S, F> Either<S, F>.mapFailure(
    transform: (value: F) -> R
): Either<S, R> {
    return when(this) {
        is Either.Failure -> {
            Either.Failure(transform(this.value))
        }
        is Either.Success -> {
            Either.Success(this.value)
        }
    }
}