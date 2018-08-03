package starter.kotlin

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.RestAssured.given
import jp.que.ti.divgroup.App
import jp.que.ti.divgroup.logic.DividerIntoGroups
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jooby.Status
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.sql.Timestamp
import java.util.*

@RunWith(JUnitPlatform::class)
object AppDividegroupJsonSpek : Spek({
    jooby(App()) {
        val jsonBody = """{"items" : ["a1","b2","c3","d4","e5","f6","g7"]
                       | ,"groupNumber": 3
                       | ,"dateTime" : "2018-1-1 23:59:59"}""".trimMargin()

        val expectedJson = DividerIntoGroups.divide(3
                , Random(Timestamp.valueOf("2018-1-1 23:59:59").time)
                , listOf("a1", "b2", "c3", "d4", "e5", "f6", "g7"))
                .let {
                    jacksonObjectMapper().writeValueAsString(it)
                }

        describe("body に json を渡した POSTリクエストで結果を取得する") {
            given("request body (json) =${jsonBody}") {
                it("グループ分けされた 一覧が返却されるべき") {
                    val name = "Jooby"
                    given().request().body(jsonBody)
//                            .queryParam("name", name)
                            .`when`().post("/dividegroup/json")
                            .then()
                            .assertThat().statusCode(Status.OK.value())
                            .extract().asString()
                            .let {
                                assertThat(it).isEqualTo(expectedJson)
                            }
                }
            }
        }
    }
})
