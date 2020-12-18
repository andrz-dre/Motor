package jsontokotlin.interceptor

import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.codeelements.KPropertyName

class MakePropertiesNameToBeCamelCaseInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            val camelCaseNameProperties = kotlinClass.properties.map {
                val camelCaseName = KPropertyName.makeLowerCamelCaseLegalNameOrEmptyName(it.originName)
                it.copy(name = camelCaseName)
            }
            kotlinClass.copy(properties = camelCaseNameProperties)
        } else {
            kotlinClass
        }

    }

}
