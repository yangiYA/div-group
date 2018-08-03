package jp.que.ti.divgroup.logic

import jp.que.ti.divgroup.logic.DividerIntoGroupsTestUtil.seed
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.*


@RunWith(JUnitPlatform::class)
class DividerIntoGroupsTest : Spek({

    given("DividerIntoGroups#divide のテスト") {

        listOf(Params(listOf("a", "b", "c", "d", "e", "f", "g"), 3) to
                Expects(listOf(3, 2, 2))
                , Params(listOf("a", "b", "c", "d", "e", "f", "g"), 4) to
                Expects(listOf(2, 2, 2, 1))
                , Params(listOf(), 2) to
                Expects(listOf(0, 0))
        ).forEach {
            val (param, expect) = it
            val inList = param.items

            /*** テスト対象のメソッド実行 ***/
            val result: List<List<String>> = DividerIntoGroups.divide(param.groupNum, seed(), param.items)
            val resultFlat = result.flatMap { it }

            on("\"${param.groupNum}つ に分割する, 元list=${inList} ,result=${result}") {
                //                on("\"${param.groupNum}つ に分割する, 元list=${param.items} ,result=${result}") {

                it("分割後List数は ${expect.size} ") {
                    assertEquals(expect.size, result.size)
                }

                it("結果Listの全ての要素数(${resultFlat.size})は　、元の要素数(${param.items.size})と同じで並び順は変わっている") {
                    assertThat(resultFlat).hasSize(param.items.size)
                    assertThat(resultFlat.size).isEqualTo(param.items.size)
                    if (param.items.size != 0)
                        assertThat(resultFlat.joinToString()).isNotEqualTo(param.items.joinToString())
                }

                it("元の要素 ${param.items} は結果resultFlat(${resultFlat}) に全て含まれている") {
                    assertThat(resultFlat).containsAll(param.items)
                }

            }

            on("分割後Listの各要素数= ${expect.itemSizeList}, 元list=${inList} ,result=${result}") {
                val itr = expect.itemSizeList.iterator()
                result.forEach {
                    it("Listの各要素数は ${it.size} , 要素=${it}") {
                        assertThat(it).hasSize(itr.next())
                    }
                }
            }

        }

        on("同じ状態のseedを使って分割すると") {
            val param = arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m")
            val result1: List<List<String>> = DividerIntoGroups.divideArray(5, seed(), *param)
            val result2: List<List<String>> = DividerIntoGroups.divideArray(5, seed(), *param)
            it("結果は毎回一致する") {
                assertThat(result1.toString()).isEqualTo(result2.toString())
            }

        }
    }
}) {
    data class Params(val items: List<String>, val groupNum: Int)
    data class Expects(val itemSizeList: List<Int>) {
        val size get() = itemSizeList.size
    }
}

internal object DividerIntoGroupsTestUtil {

    fun seed(): Random {
        return Random(1L)
    }

}



