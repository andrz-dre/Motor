package jsontokotlin.extensions.ted.zeng

import jsontokotlin.extensions.Extension
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.classscodestruct.Property

/**
 * Created by ted on 2019-06-13 18:10.
 */
object PropertyAnnotationLineSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "ted.zeng.property_annotation_in_same_line_enable"

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        return if (kotlinClass is DataClass) {
            if (getConfig(configKey).toBoolean()) {
                kotlinClass.properties.forEach(Property::letLastAnnotationStayInSameLine)
            }
            kotlinClass
        } else {
            kotlinClass
        }
    }

}