package jsontokotlin.interceptor.annotations.moshi

import jsontokotlin.interceptor.IKotlinClassInterceptor
import jsontokotlin.model.classscodestruct.Annotation
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.codeannotations.MoshiPropertyAnnotationTemplate
import jsontokotlin.model.codeelements.KPropertyName

/**
 * This interceptor try to add Moshi(code gen) annotation
 */
class AddMoshiCodeGenAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            val addMoshiCodeGenAnnotationProperties = kotlinClass.properties.map {
                val camelCaseName = KPropertyName.makePropertyName(it.originName, true)
                it.copy(annotations = MoshiPropertyAnnotationTemplate(it.originName).getAnnotations(), name = camelCaseName)
            }
            val classAnnotationString = "@JsonClass(generateAdapter = true)"
            val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)
            kotlinClass.copy(properties = addMoshiCodeGenAnnotationProperties, annotations = listOf(classAnnotation))
        } else {
            kotlinClass
        }
    }
}