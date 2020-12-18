package jsontokotlin.extensions.jose.han

import jsontokotlin.extensions.Extension
import jsontokotlin.model.classscodestruct.Annotation
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass

/**
 *  @author jose.han
 *  @Date 2019/7/27Ø
 */
object ParcelableAnnotationSupport : Extension() {

    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "jose.han.add_parcelable_annotatioin_enable"

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass) {
            if (getConfig(configKey).toBoolean()) {
                val classAnnotationString1 = "@SuppressLint(\"ParcelCreator\")"
                val classAnnotationString2 = "@Parcelize"

                val classAnnotation1 = Annotation.fromAnnotationString(classAnnotationString1)
                val classAnnotation2 = Annotation.fromAnnotationString(classAnnotationString2)

                val newAnnotations = mutableListOf(classAnnotation1, classAnnotation2).also { it.addAll(kotlinClass.annotations) }

                return kotlinClass.copy(annotations = newAnnotations, parentClassTemplate = "Parcelable")
            }
        }
        return kotlinClass
    }

    override fun intercept(originClassImportDeclaration: String): String {

        val classAnnotationImportClassString = "import kotlinx.android.parcel.Parcelize".append("import android.os.Parcelable")

        return if (getConfig(configKey).toBoolean()) {
            originClassImportDeclaration.append(classAnnotationImportClassString)
        } else {
            originClassImportDeclaration
        }
    }
}
