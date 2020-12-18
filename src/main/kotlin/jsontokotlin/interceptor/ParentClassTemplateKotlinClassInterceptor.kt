package jsontokotlin.interceptor

import jsontokotlin.model.ConfigManager
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass

class ParentClassTemplateKotlinClassInterceptor : IKotlinClassInterceptor<KotlinClass> {

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            val parentClassTemplateSimple = ConfigManager.parenClassTemplate.substringAfterLast(".")
            kotlinClass.copy(parentClassTemplate = parentClassTemplateSimple)
        } else {
            kotlinClass
        }
    }
}