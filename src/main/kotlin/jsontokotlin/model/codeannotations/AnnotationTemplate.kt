package jsontokotlin.model.codeannotations

import jsontokotlin.model.classscodestruct.Annotation

interface AnnotationTemplate {
    fun getCode():String
    fun getAnnotations():List<Annotation>
}