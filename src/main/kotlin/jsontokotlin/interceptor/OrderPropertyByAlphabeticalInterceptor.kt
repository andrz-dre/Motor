package jsontokotlin.interceptor

import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass


class OrderPropertyByAlphabeticalInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            val orderByAlphabeticalProperties = kotlinClass.properties.sortedBy { it.name }
            kotlinClass.copy(properties = orderByAlphabeticalProperties)
        } else {
            kotlinClass
        }

    }
}

