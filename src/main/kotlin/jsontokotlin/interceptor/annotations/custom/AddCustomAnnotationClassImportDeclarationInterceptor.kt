package jsontokotlin.interceptor.annotations.custom

import jsontokotlin.interceptor.IImportClassDeclarationInterceptor
import jsontokotlin.model.ConfigManager

class AddCustomAnnotationClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {

    override fun intercept(originClassImportDeclaration: String): String {
        val propertyAnnotationImportClassString = ConfigManager.customAnnotationClassImportdeclarationString
        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
    }
}
