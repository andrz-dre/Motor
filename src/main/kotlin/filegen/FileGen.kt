package filegen

interface FileGen {
    fun generate(fileName: String, content: String, filePath: String? = null)
}