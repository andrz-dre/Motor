package jsontokotlin.extensions.xu.rui

import javax.swing.JPanel
import jsontokotlin.extensions.Extension
import jsontokotlin.model.ConfigManager
import jsontokotlin.model.DefaultValueStrategy
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.codeelements.getDefaultValue
import jsontokotlin.utils.NULLABLE_PRIMITIVE_TYPES
import jsontokotlin.utils.getNonNullPrimitiveType

object PrimitiveTypeNonNullableSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "xu.rui.force_primitive_type_non-nullable"


    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (getConfig(configKey).toBoolean().not()) {
            return kotlinClass
        }

        if (kotlinClass is DataClass) {

            val primitiveTypeNonNullableProperties = kotlinClass.properties.map {
                if (it.type in NULLABLE_PRIMITIVE_TYPES) {
                    val newType = getNonNullPrimitiveType(it.type)
                    if (ConfigManager.defaultValueStrategy != DefaultValueStrategy.None) {
                        it.copy(type = newType, value = getDefaultValue(newType))
                    } else {
                        it.copy(type = newType)
                    }
                } else {
                    it
                }
            }

            return kotlinClass.copy(properties = primitiveTypeNonNullableProperties)
        } else {
            return kotlinClass
        }
    }
}