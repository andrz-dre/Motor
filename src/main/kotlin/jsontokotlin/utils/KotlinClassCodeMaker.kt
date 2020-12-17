package jsontokotlin.utils

import jsontokotlin.interceptor.IKotlinClassInterceptor
import jsontokotlin.interceptor.InterceptorManager
import jsontokotlin.model.ConfigManager
import jsontokotlin.model.classscodestruct.KotlinClass

class KotlinClassCodeMaker(private val kotlinClass: KotlinClass) {

    fun makeKotlinClassCode(): String {
        val interceptors = InterceptorManager.getEnabledKotlinDataClassInterceptors()
        return makeKotlinClassCode(interceptors)
    }

    private fun makeKotlinClassCode(interceptors: List<IKotlinClassInterceptor<KotlinClass>>): String {
        var kotlinClassForCodeGenerate = kotlinClass
        kotlinClassForCodeGenerate = kotlinClassForCodeGenerate.applyInterceptors(interceptors)
        return if (ConfigManager.isInnerClassModel) {
            kotlinClassForCodeGenerate.getCode()
        } else {
            val resolveNameConflicts = kotlinClassForCodeGenerate.resolveNameConflicts()
            val allModifiableClassesRecursivelyIncludeSelf = resolveNameConflicts
                    .getAllModifiableClassesRecursivelyIncludeSelf()
            allModifiableClassesRecursivelyIncludeSelf
                    .joinToString("\n\n") { it.getOnlyCurrentCode() }
        }
    }
}
