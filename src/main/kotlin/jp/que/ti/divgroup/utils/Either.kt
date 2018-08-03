package jp.que.ti.divgroup.utils


@Suppress("unused")
sealed class Either<out L, out R>

data class Left<out L>(val value: L) : Either<L, Nothing>()
data class Right<out R>(val value: R) : Either<Nothing, R>()

inline fun <L, R, T> Either<L, R>.flatMap(f: (R) -> Either<L, T>): Either<L, T> =
        when (this) {
            is Left -> this
            is Right -> f(this.value)
        }

inline fun <L, R, T> Either<L, R>.map(f: (R) -> T): Either<L, T> =
        flatMap { Right(f(it)) }