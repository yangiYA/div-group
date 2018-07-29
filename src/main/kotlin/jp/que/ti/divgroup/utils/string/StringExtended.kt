package jp.que.ti.divgroup.utils.string

import jp.que.ti.divgroup.utils.string.StringExtended.log
import org.slf4j.LoggerFactory
import java.lang.RuntimeException
import java.sql.Timestamp

object StringExtended {
    val log = LoggerFactory.getLogger("jp.que.ti.divgroup.utils.string.StringExtended")
}

fun String.toDate(): java.util.Date? {
    val rtn: java.util.Date? = this.toTimestamp()?.let { x -> java.util.Date(x.getTime()) }
    return rtn
}

fun String.toTimestamp(): Timestamp? {
    val s1 = if (this.contains('/')) {
        this.trim().replace('/', '-')
    } else {
        this.trim()
    }
    val s2 = if (s1.contains(' ')) {
        s1
    } else {
        s1 + " 00:00:00"
    }
    try {
        return Timestamp.valueOf(s2)
    } catch (e: RuntimeException) {
        log.info("*** datetime cannot parse", e)
        return null
    }
}
