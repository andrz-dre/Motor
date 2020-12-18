package jsontokotlin.extensions.wu.seal

import javax.swing.JPanel
import jsontokotlin.extensions.Extension
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass

object PropertySuffixSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val suffixKeyEnable = "wu.seal.property_suffix_enable"
    @Suppress("MemberVisibilityCanBePrivate")
    const val suffixKey = "wu.seal.property_suffix"

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (kotlinClass is DataClass) {

            return if (getConfig(suffixKeyEnable).toBoolean() && getConfig(suffixKey).isNotEmpty()) {
                val originProperties = kotlinClass.properties
                val newProperties = originProperties.map {
                    val suffix = getConfig(suffixKey)
                    if (it.name.isNotEmpty()) {
                        val newName = it.name + suffix.first().toUpperCase() + suffix.substring(1)
                        it.copy(name = newName)
                    } else it
                }
                kotlinClass.copy(properties = newProperties)
            } else {
                kotlinClass
            }
        } else {
            return kotlinClass
        }
    }
}