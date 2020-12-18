package jsontokotlin.interceptor.annotations.serializable

import jsontokotlin.interceptor.IKotlinClassInterceptor
import jsontokotlin.model.classscodestruct.Annotation
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.codeannotations.SerializablePropertyAnnotationTemplate
import jsontokotlin.model.codeelements.KPropertyName

class AddSerializableAnnotationInterceptor: IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (kotlinClass is DataClass) {
            val addCustomAnnotationProperties = kotlinClass.properties.map {
                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)
                val annotations = SerializablePropertyAnnotationTemplate(it.originName).getAnnotations()
                it.copy(annotations = annotations,name = camelCaseName)
            }
            val classAnnotationString = "@Serializable"
            val classAnnotation = Annotation.fromAnnotationString(classAnnotationString)
            return kotlinClass.copy(properties = addCustomAnnotationProperties,annotations = listOf(classAnnotation))
        } else {
            return kotlinClass
        }
    }
}