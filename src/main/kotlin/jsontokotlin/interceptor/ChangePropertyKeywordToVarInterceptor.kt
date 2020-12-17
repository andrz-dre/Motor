package jsontokotlin.interceptor

import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass

class ChangePropertyKeywordToVarInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            val varProperties = kotlinClass.properties.map {
                it.copy(keyword = "var")
            }
            kotlinClass.copy(properties = varProperties)
        } else {
            kotlinClass
        }
    }

}
