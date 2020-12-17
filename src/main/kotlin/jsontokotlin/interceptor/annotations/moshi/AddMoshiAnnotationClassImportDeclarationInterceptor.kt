package jsontokotlin.interceptor.annotations.moshi

import jsontokotlin.interceptor.IImportClassDeclarationInterceptor

class AddMoshiAnnotationClassImportDeclarationInterceptor : IImportClassDeclarationInterceptor {

    override fun intercept(originClassImportDeclaration: String): String {
        val propertyAnnotationImportClassString =  "import com.squareup.moshi.Json"
        return originClassImportDeclaration.append(propertyAnnotationImportClassString)
    }
}
