package filegen

import java.io.File

class KotlinFileGen : FileGen {

    override fun generate(fileName: String, content: String, filePath: String?) {
        val generatedFile = StringBuilder()
        filePath?.let {
            generatedFile.append(it)
        }
        createDirIfNotExist(generatedFile.toString())
        generatedFile.append("/$fileName")
        File(generatedFile.toString()).printWriter().use { out -> out.println(content) }
    }

    private fun createDirIfNotExist(path: String) {
        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdir()
        }
    }
}