package starter.kotlin

import io.restassured.RestAssured.get
import io.restassured.RestAssured.given
import jp.que.ti.divgroup.App
import org.assertj.core.api.Assertions
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jooby.Status
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
object AppTest : Spek({
    jooby(App()) {
        describe("Get with query parameter") {
            given("queryParameter name=Kotlin") {
                it("should contain Hello [name]!") {
                    val name = "Jooby"
                    given()
                            .queryParam("name", name)
                            .`when`().get("/")
                            .then()
                            .assertThat().statusCode(Status.OK.value())
                            .extract().asString()
                            .let {
                                Assertions.assertThat(it).contains("Hello ${name}!")
                            }
                }
            }

            given("Get without query parameter") {
                it("should return Kooby as the default name") {
                    get("/")
                            .then()
                            .assertThat().statusCode(Status.OK.value())
                            .extract().asString()
                            .let {
                                Assertions.assertThat(it).contains("Hello グループ分け!")
                            }
                }
            }
        }
    }
})
