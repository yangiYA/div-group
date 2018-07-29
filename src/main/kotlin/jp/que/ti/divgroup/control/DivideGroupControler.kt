package jp.que.ti.divgroup.control

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jp.que.ti.divgroup.err.ErrWithBody400
import jp.que.ti.divgroup.logic.DividerIntoGroups
import jp.que.ti.divgroup.utils.Either
import jp.que.ti.divgroup.utils.Left
import jp.que.ti.divgroup.utils.Right
import jp.que.ti.divgroup.utils.string.toDate
import org.jooby.MediaType
import org.jooby.Request
import org.jooby.Result
import org.jooby.Results
import org.slf4j.LoggerFactory
import java.util.*

object DivideGroupControler {
    val log = LoggerFactory.getLogger(this.javaClass)

    private val mapper = jacksonObjectMapper()

    fun json(req: Request): Result {

        val inEither = DivideGroupControler.inJsonOutJson(req)

        /*** 入力チェック ***/
        val param = when (inEither) {
            is Right<DivideGroupControler.ParameterJson> -> inEither.value
            is Left -> throw ErrWithBody400("{}")
        }

        val rtn = DividerIntoGroups.divide(
                param.groupNumber
                , Random(param.dateTime.time), param.items
        )
        return rtn.let {
            ObjectMapper().writeValueAsString(it)
        }.let { Results.ok(it).type(MediaType.json) }
    }

    private fun inJsonOutJson(req: Request): Either<List<String>, ParameterJson> {
        val bdy = req.body().value()
        val j: ParameterRawJson = mapper.readValue(bdy)

        val either = j.validate()
        log.debug("""****
                  |* either={}
                  |* , j={}""".trimMargin(), either, j)
        return either

    }

    data class ParameterRawJson(
            var items: List<String>?
            , var groupNumber: String?
            , var dateTime: String?
    ) {
        fun validate(): Either<List<String>, ParameterJson> {
            val gn: Int? = groupNumber?.toIntOrNull()

            val dt: java.util.Date? = dateTime?.let { x ->
                when {
                    x.isNullOrBlank() -> Date()
                    else -> x.toDate()
                }
            }

            val pj: ParameterJson? = gn?.let {
                dt?.let {
                    ParameterJson(items ?: emptyList(), gn, dt)
                }
            }

            val rtn: Either<List<String>, ParameterJson> = when (pj) {
                null -> {
                    val errors = when (gn) {
                        null -> listOf("groupNumber is Invalid.")
                        else -> emptyList()
                    } + when (dt) {
                        null -> listOf("dateTime is Invalid.")
                        else -> emptyList()
                    }
                    Left(errors)
                }
                else -> Right(pj)
            }
            return rtn
        }
    }

    data class ParameterJson(
            var items: List<String>
            , var groupNumber: Int
            , var dateTime: java.util.Date
    )

}