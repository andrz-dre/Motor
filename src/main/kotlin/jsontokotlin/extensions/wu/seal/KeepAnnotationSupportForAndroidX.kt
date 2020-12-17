package jsontokotlin.extensions.wu.seal

import javax.swing.JPanel
import jsontokotlin.extensions.Extension
import jsontokotlin.model.classscodestruct.Annotation
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass

/**
 * @author Seal.Wu
 * create at 2019/11/03
 * description:
 */
object KeepAnnotationSupportForAndroidX : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "wu.seal.add_keep_annotation_enable_androidx"

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
            return  kotlinClass
        }

    }

    override fun intercept(originClassImportDeclaration: String): String {

        val classAnnotationImportClassString = "import androidx.annotation.Keep"

        return if (getConfig(configKey).toBoolean()) {
            originClassImportDeclaration.append(classAnnotationImportClassString)
        } else {
            originClassImportDeclaration
        }
    }
}