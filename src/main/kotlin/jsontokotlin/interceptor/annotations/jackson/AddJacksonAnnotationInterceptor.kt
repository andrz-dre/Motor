package jsontokotlin.interceptor.annotations.jackson

import jsontokotlin.interceptor.IKotlinClassInterceptor
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.codeannotations.JacksonPropertyAnnotationTemplate
import jsontokotlin.model.codeelements.KPropertyName


class AddJacksonAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            val addMoshiCodeGenAnnotationProperties = kotlinClass.properties.map {
                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)
                it.copy(annotations =  JacksonPropertyAnnotationTemplate(it.originName).getAnnotations(),name = camelCaseName)
            }
            kotlinClass.copy(properties = addMoshiCodeGenAnnotationProperties)
        } else {
            kotlinClass
        }
    }
}
