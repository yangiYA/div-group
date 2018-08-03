package jp.que.ti.divgroup.logic

import jp.que.ti.divgroup.utils.list.headTail
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.collections.ArrayList


/**
 * グループ分割する機能です
 */
object DividerIntoGroups {
    val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 要素を groupNumber の数のグループに分割します
     * @param groupNumber 分割したいグループ数
     * @param seedRandom  種となる [Random]
     * @param items 分割したい要素
     */
    fun <T> divideArray(groupNumber: Int
                        , seedRandom: Random = Random(System.currentTimeMillis())
                        , vararg items: T)
            : List<List<T>> = divide(groupNumber, seedRandom, items.toList())


    /**
     * 要素を groupNumber の数のグループに分割します
     * @param groupNumber 分割したいグループ数
     * @param seedRandom  種となる [Random]
     * @param items 分割したい要素の [List]
     */
    fun <T> divide(groupNumber: Int
                   , seedRandom: Random
                   , items: List<T>): List<List<T>> {

        val tempList: ArrayList<T> = ArrayList(items)
        tempList.shuffle(seedRandom)
        val size = items.size
        val memberNum = size / groupNumber
        val rest = size % groupNumber

        val addMemberSeq: Sequence<Int> =
                generateSequence(1) { it }.take(rest)  // 余りが出た分 : 1人多くする
                        .plus(generateSequence(0) { it }.take(groupNumber - rest))

        var initTailAndResult = SourceAndAccumulator(tempList, ArrayList())

        val tailAndResult = addMemberSeq
                .map {
                    it
                }
                .fold(initTailAndResult) { acc, addMemberNum ->
                    val (h: List<T>, t: List<T>) = acc.srcList.headTail(memberNum + addMemberNum)
                    acc.accum.add(h)
                    SourceAndAccumulator(t, acc.accum)
                }

        return tailAndResult.accum
    }

    internal data class SourceAndAccumulator<T>(val srcList: List<T>, val accum: MutableList<List<T>>)
}