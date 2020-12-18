/*
 json Location : /Users/ovo/JsonTesting/testing.json
 /Users/ovo/IdeaProjects/Motor/src/main/kotlin
* */
import Arguments.FILE_PATH
import Arguments.HELP
import Arguments.SAVE_PATH
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.File

fun main(args: Array<String>) {

    if (args.contains(HELP)) {
        displayHelp()
        return
    }

    if (args.contains(FILE_PATH)) {
        val filePath = getFilePath(args)
        val apiBlueprintContent = readFile(filePath)
        val apiElement = Gson().fromJson(apiBlueprintContent, JsonObject::class.java)
        val savePath = if (args.contains(SAVE_PATH)) {
            getSavePath(args)
        } else {
            System.getProperty("user.dir")
        }

        ApiElementParser.generateResponseAndRequest(apiElement, savePath)
        return
    }
}

private fun getFilePath(args: Array<String>) = args[1 + args.indexOf(FILE_PATH)]

private fun getSavePath(args: Array<String>): String {
    val savePath = args[1 + args.indexOf(SAVE_PATH)]
    return if (savePath.isNotEmpty()) {
        savePath
    } else {
        System.getProperty("user.dir")
    }
}

private fun displayHelp() {
    println()
    println("$FILE_PATH \t used for defining api element path")
    println()
}

private fun readFile(file: String): String {
    return try {
        File(file).readText(Charsets.UTF_8)
    } catch (ignored: Exception) {
        "File not found or not defined, use -h for help."
    }
}
