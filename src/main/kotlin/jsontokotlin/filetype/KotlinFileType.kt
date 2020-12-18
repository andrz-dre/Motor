package jsontokotlin.filetype

import javax.swing.Icon

/**
 * Created by Seal.Wu on 2018/4/18.
 */

class KotlinFileType {
    fun getCharset(p1: ByteArray): String? {
        return null
    }

    fun getDefaultExtension(): String {
        return ".kt"
    }

    fun getIcon(): Icon? {
        return null
    }

    fun getName(): String {
        return "Kotlin file"

    }

    fun getDescription(): String {
        return "Kotlin source file"

    }

    fun isBinary(): Boolean {
        return false
    }

    fun isReadOnly(): Boolean {
        return false
    }
}
