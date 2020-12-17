package jsontokotlin.interceptor.annotations.custom

import jsontokotlin.interceptor.IKotlinClassInterceptor
import jsontokotlin.model.ConfigManager
import jsontokotlin.model.classscodestruct.Annotation
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.codeannotations.CustomPropertyAnnotationTemplate
import jsontokotlin.model.codeelements.KPropertyName

class AddCustomAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (kotlinClass is DataClass) {
            val addCustomAnnotationProperties = kotlinClass.properties.map {
                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)
                val annotations = CustomPropertyAnnotationTemplate(it.originName).getAnnotations()
                it.copy(annotations = annotations,name = camelCaseName)
            }
            val classAnnotationString = ConfigManager.customClassAnnotationFormatString
            val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)
            return kotlinClass.copy(properties = addCustomAnnotationProperties,annotations = listOf(classAnnotation))
        } else {
            return kotlinClass
        }
    }
}
