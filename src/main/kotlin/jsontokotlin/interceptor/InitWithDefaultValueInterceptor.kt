package jsontokotlin.interceptor

import jsontokotlin.model.ConfigManager
import jsontokotlin.model.DefaultValueStrategy
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.codeelements.getDefaultValue
import jsontokotlin.model.codeelements.getDefaultValueAllowNull

class InitWithDefaultValueInterceptor : IKotlinClassInterceptor<KotlinClass> {
    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        return if (kotlinClass is DataClass) {
            val dataClass: DataClass = kotlinClass
            val initWithDefaultValueProperties = dataClass.properties.map {
                val initDefaultValue = if (ConfigManager.defaultValueStrategy == DefaultValueStrategy.AvoidNull) getDefaultValue(it.type)
                else getDefaultValueAllowNull(it.type)
                it.copy(value = initDefaultValue)
            }
            dataClass.copy(properties = initWithDefaultValueProperties)
        } else {
            kotlinClass
        }
    }
}
