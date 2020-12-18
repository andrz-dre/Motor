package jsontokotlin.interceptor.annotations.fastjson

import jsontokotlin.interceptor.IKotlinClassInterceptor
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.codeannotations.FastjsonPropertyAnnotationTemplate
import jsontokotlin.model.codeelements.KPropertyName

class AddFastJsonAnnotationInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            val addFastJsonAnnotationProperties = kotlinClass.properties.map {
                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalName(it.originName)
                val annotations = FastjsonPropertyAnnotationTemplate(it.originName).getAnnotations()
                it.copy(annotations = annotations, name = camelCaseName)
            }
            kotlinClass.copy(properties = addFastJsonAnnotationProperties)
        } else {
            kotlinClass
        }
    }

}
