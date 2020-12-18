package jsontokotlin.feedback

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 *
 * Created by Seal.Wu on 2017/9/25.
 */

/**
 * handler the exception
 */

val prettyPrintGson: Gson = GsonBuilder().setPrettyPrinting().create()

fun getUncaughtExceptionHandler(jsonString: String, callBack: () -> Unit): Thread.UncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, e ->
    val logBuilder = StringBuilder()
    logBuilder.append("\n\n")
    logBuilder.append("user: $UUID").append("\n")
    val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss E", Locale.CHINA).format(Date())
    logBuilder.append("createTime: $time").append("\n")

    logBuilder.appendln().append(getConfigInfo()).appendln()
    val stringWriter = StringWriter()
    val printWriter = PrintWriter(stringWriter, true)
    e.printStackTrace(printWriter)
    var cause = e.cause
    while (cause != null) {
        cause.printStackTrace(printWriter)
        cause = cause.cause
    }
    printWriter.close()
    logBuilder.append(stringWriter.toString())

    logBuilder.append("Error Json String:\n")
    logBuilder.append(jsonString)
    Thread {
        sendExceptionLog(logBuilder.toString())
    }.start()

    callBack.invoke()
}

/**
 * get the config info of this plugin settings
 */
fun getConfigInfo(): String {
    return prettyPrintGson.toJson(ConfigInfo())
}

fun dealWithException(jsonString: String, e: Throwable) {
    var jsonString1 = jsonString
}