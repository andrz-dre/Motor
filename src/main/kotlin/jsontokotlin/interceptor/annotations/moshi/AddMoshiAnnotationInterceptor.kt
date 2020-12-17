package jsontokotlin.interceptor.annotations.moshi

import jsontokotlin.interceptor.IKotlinClassInterceptor
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.codeannotations.MoshiPropertyAnnotationTemplate
import jsontokotlin.model.codeelements.KPropertyName

class AddMoshiAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {


    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            val addMoshiCodeGenAnnotationProperties = kotlinClass.properties.map {
                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)
                it.copy(annotations = MoshiPropertyAnnotationTemplate(it.originName).getAnnotations(), name = camelCaseName)
            }
            kotlinClass.copy(properties = addMoshiCodeGenAnnotationProperties)
        } else {
            kotlinClass
        }

    }
}
