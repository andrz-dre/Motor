package jsontokotlin.extensions.chen.biao

import jsontokotlin.extensions.Extension
import jsontokotlin.model.classscodestruct.Annotation
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass

/**
 * @author chenbiao
 * create at 2019/5/16
 * description:
 */
object KeepAnnotationSupport : Extension() {


    @Suppress("MemberVisibilityCanBePrivate")
    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    const val configKey = "chen.biao.add_keep_annotation_enable"

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {
            return if (getConfig(configKey).toBoolean()) {

                val classAnnotationString = "@Keep"

                val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)

                val newAnnotations = mutableListOf(classAnnotation).also { it.addAll(kotlinClass.annotations) }

                return kotlinClass.copy(annotations = newAnnotations)
            } else {
                kotlinClass
            }
        } else {
            return kotlinClass
        }

    }

    override fun intercept(originClassImportDeclaration: String): String {

        val classAnnotationImportClassString = "import android.support.annotation.Keep"

        return if (getConfig(configKey).toBoolean()) {
            originClassImportDeclaration.append(classAnnotationImportClassString)
        } else {
            originClassImportDeclaration
        }
    }
}