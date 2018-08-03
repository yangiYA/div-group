package jp.que.ti.divgroup

import jp.que.ti.divgroup.control.DivideGroupControler
import jp.que.ti.divgroup.err.ErrWithBody
import org.jooby.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.jooby.ftl.Ftl
import java.nio.file.Paths


/**
 * Kotlin stater project.
 */
class App : Kooby({


    /***************************
     * using Modules
     ***************************/
    use(Ftl())

    /***************************
     * Session : use cookie implementation
     ***************************/
    cookieSession();

    /***************************
     * Static files (in public directory)
     ***************************/
    assets("/js/**")
    assets("/css/**")

    /***************************
     * for Javascript unitTest Directory
     ***************************/
    assets("/js_test/**", Paths.get("js_test/"))
    /*** in classpath case ***/
    //assets("/**", "assets/{0}");

    /***************************
     * Error Handling
     ***************************/
    err(ErrWithBody::class.java) { req, rsp, err ->
        val cause = err.cause
        if (cause is ErrWithBody) {
            if (cause.status == Status.SERVER_ERROR) log.error("${cause.message} body=${cause.body}", err)

            rsp.send(
                    Result().status(cause.status).set(cause.body))
        } else {
            Err.DefHandler().handle(req, rsp, err)
        }
    }

    /***************************
     * Rooting
     ***************************/
    get("/") { req ->
        req.set("name", req.param("name").value("グループ分け"))
        Results.html("freemarker/index")
    }

    post("dividegroup/json") { req -> DivideGroupControler.json(req) }

}) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(App.javaClass)
    }
}

/**
 * Run application:
 */
fun main(args: Array<String>) {
    LoggerFactory.getLogger(App.javaClass).info("Server starting ...")
    run(::App, *args)
}
