package jp.que.ti.divgroup.err

import org.jooby.Err
import org.jooby.Status

open class ErrWithBody(val body: String, val status: Status, cause: Throwable? = null) : RuntimeException(status.reason(), cause)

class ErrWithBody400(body: String) : ErrWithBody(body, Status.BAD_REQUEST)
class ErrWithBody404(body: String) : ErrWithBody(body, Status.NOT_FOUND)
class ErrWithBody500(body: String) : ErrWithBody(body, Status.SERVER_ERROR)
