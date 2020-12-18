package jsontokotlin.extensions.wu.seal

import javax.swing.JPanel
import jsontokotlin.extensions.Extension
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.utils.getChildType
import jsontokotlin.utils.getRawType

object ClassNameSuffixSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val suffixKeyEnable = "wu.seal.class_name_suffix_enable"
    @Suppress("MemberVisibilityCanBePrivate")
    const val suffixKey = "wu.seal.class_name_suffix"

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        return if (kotlinClass is DataClass) {
            val suffix = getConfig(suffixKey)
            return if (getConfig(suffixKeyEnable).toBoolean() && suffix.isNotEmpty()) {
                val standTypes = listOf("Int", "Double", "Long", "String", "Boolean")
                val originName = kotlinClass.name
                val newPropertyTypes =
                        kotlinClass.properties.map {
                            val rawSubType = getChildType(getRawType(it.type))
                            when {
                                it.type.isMapType() -> {
                                    it.type//currently don't support map type
                                }
                                standTypes.contains(rawSubType) -> it.type
                                else -> it.type.replace(rawSubType, rawSubType + suffix)
                            }
                        }

                val newPropertyDefaultValues = kotlinClass.properties.map {
                    val rawSubType = getChildType(getRawType(it.type))
                    when {
                        it.value.isEmpty() -> it.value
                        it.type.isMapType() -> {
                            it.value//currently don't support map type
                        }
                        standTypes.contains(rawSubType) -> it.value
                        else -> it.value.replace(rawSubType, rawSubType + suffix)
                    }
                }

                val newProperties = kotlinClass.properties.mapIndexed { index, property ->

                    val newType = newPropertyTypes[index]

                    val newValue = newPropertyDefaultValues[index]

                    property.copy(type = newType, value = newValue)
                }
                kotlinClass.copy(name = originName + suffix, properties = newProperties)
            } else {
                kotlinClass
            }

        } else {
            kotlinClass
        }

    }

    private fun String.isMapType(): Boolean {
        return matches(Regex("Map<.+,.+>"))
    }
}

