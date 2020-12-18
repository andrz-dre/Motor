package jsontokotlin.model.codeannotations

import jsontokotlin.model.classscodestruct.Annotation
import jsontokotlin.model.ConfigManager

class CustomPropertyAnnotationTemplate(val rawName: String) : AnnotationTemplate {

    private val annotation = Annotation(ConfigManager.customPropertyAnnotationFormatString, rawName)

    override fun getCode(): String {
        return annotation.getAnnotationString()
    }

    override fun getAnnotations(): List<Annotation> {
        return listOf(annotation)
    }

}