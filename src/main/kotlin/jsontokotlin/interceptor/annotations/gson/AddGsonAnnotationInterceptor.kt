package jsontokotlin.interceptor.annotations.gson

import jsontokotlin.interceptor.IKotlinClassInterceptor
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.codeannotations.GsonPropertyAnnotationTemplate
import jsontokotlin.model.codeelements.KPropertyName

class AddGsonAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {


    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            val addGsonAnnotationProperties = kotlinClass.properties.map {
                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)
                it.copy(annotations = GsonPropertyAnnotationTemplate(it.originName).getAnnotations(), name = camelCaseName)
            }
            kotlinClass.copy(properties = addGsonAnnotationProperties)
        } else {
            kotlinClass
        }
    }

}
