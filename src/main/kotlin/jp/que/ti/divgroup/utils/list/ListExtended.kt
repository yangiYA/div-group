package jp.que.ti.divgroup.utils.list

fun <T> List<T>.headTail(number: Int): Pair<List<T>, List<T>> = when {
    size == 0 -> Pair<List<T>, List<T>>(emptyList(), emptyList())
    size <= number -> Pair<List<T>, List<T>>(this, emptyList())
    else -> Pair(subList(0, number)
            , subList(number, size))
}

fun <T> List<T>.headTail(): Pair<T, List<T>> = when (size) {
    0 -> throw IndexOutOfBoundsException("Parameter list size is zero!")
    1 -> Pair<T, List<T>>(this[0], emptyList())
    else -> Pair<T, List<T>>(this[0], subList(1, size))
}

fun <T> List<T>.asArray(): Array<String?> {
    val tmpArray = when (this) {
        is java.util.AbstractList<T> -> this
        else -> ArrayList<T>(this)
    }.let {
        val ar = arrayOfNulls<String?>(this.size)
        it.toArray(ar)
    }
    return tmpArray
}


