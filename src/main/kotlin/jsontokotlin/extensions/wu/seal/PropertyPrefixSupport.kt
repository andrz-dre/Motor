package jsontokotlin.extensions.wu.seal

import javax.swing.JPanel
import jsontokotlin.extensions.Extension
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass

object PropertyPrefixSupport : Extension() {

    const val prefixKeyEnable = "wu.seal.property_prefix_enable"
    const val prefixKey = "wu.seal.property_prefix"

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {

            return if (getConfig(prefixKeyEnable).toBoolean() && getConfig(prefixKey).isNotEmpty()) {
                val originProperties = kotlinClass.properties
                val newProperties = originProperties.map {
                    val prefix = getConfig(prefixKey)
                    if (it.name.isNotEmpty()) {
                        val newName = prefix + it.name.first().toUpperCase() + it.name.substring(1)
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