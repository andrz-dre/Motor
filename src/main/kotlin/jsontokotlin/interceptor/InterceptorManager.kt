package jsontokotlin.interceptor

import jsontokotlin.extensions.ExtensionsCollector
import jsontokotlin.interceptor.annotations.custom.AddCustomAnnotationClassImportDeclarationInterceptor
import jsontokotlin.interceptor.annotations.custom.AddCustomAnnotationInterceptor
import jsontokotlin.interceptor.annotations.fastjson.AddFastJsonAnnotationInterceptor
import jsontokotlin.interceptor.annotations.fastjson.AddFastjsonAnnotationClassImportDeclarationInterceptor
import jsontokotlin.interceptor.annotations.gson.AddGsonAnnotationClassImportDeclarationInterceptor
import jsontokotlin.interceptor.annotations.gson.AddGsonAnnotationInterceptor
import jsontokotlin.interceptor.annotations.jackson.AddJacksonAnnotationClassImportDeclarationInterceptor
import jsontokotlin.interceptor.annotations.jackson.AddJacksonAnnotationInterceptor
import jsontokotlin.interceptor.annotations.logansquare.AddLoganSquareAnnotationClassImportDeclarationInterceptor
import jsontokotlin.interceptor.annotations.logansquare.AddLoganSquareAnnotationInterceptor
import jsontokotlin.interceptor.annotations.moshi.AddMoshiAnnotationClassImportDeclarationInterceptor
import jsontokotlin.interceptor.annotations.moshi.AddMoshiAnnotationInterceptor
import jsontokotlin.interceptor.annotations.moshi.AddMoshiCodeGenAnnotationInterceptor
import jsontokotlin.interceptor.annotations.moshi.AddMoshiCodeGenClassImportDeclarationInterceptor
import jsontokotlin.interceptor.annotations.serializable.AddSerializableAnnotationClassImportDeclarationInterceptor
import jsontokotlin.interceptor.annotations.serializable.AddSerializableAnnotationInterceptor
import jsontokotlin.model.ConfigManager
import jsontokotlin.model.DefaultValueStrategy
import jsontokotlin.model.TargetJsonConverter
import jsontokotlin.model.classscodestruct.KotlinClass

object InterceptorManager {

    fun getEnabledKotlinDataClassInterceptors(): List<IKotlinClassInterceptor<KotlinClass>> {
        return mutableListOf<IKotlinClassInterceptor<KotlinClass>>().apply {
            if (ConfigManager.isPropertiesVar) {
                add(ChangePropertyKeywordToVarInterceptor())
            }
            add(PropertyTypeNullableStrategyInterceptor())
            if (ConfigManager.defaultValueStrategy != DefaultValueStrategy.None) {
                add(InitWithDefaultValueInterceptor())
            }
            when (ConfigManager.targetJsonConverterLib) {
                TargetJsonConverter.None -> {
                }
                TargetJsonConverter.NoneWithCamelCase -> add(MakePropertiesNameToBeCamelCaseInterceptor())
                TargetJsonConverter.Gson -> add(AddGsonAnnotationInterceptor())
                TargetJsonConverter.FastJson -> add(AddFastJsonAnnotationInterceptor())
                TargetJsonConverter.Jackson -> add(AddJacksonAnnotationInterceptor())
                TargetJsonConverter.MoShi -> add(AddMoshiAnnotationInterceptor())
                TargetJsonConverter.MoshiCodeGen -> add(AddMoshiCodeGenAnnotationInterceptor())
                TargetJsonConverter.LoganSquare -> add(AddLoganSquareAnnotationInterceptor())
                TargetJsonConverter.Custom -> add(AddCustomAnnotationInterceptor())
                TargetJsonConverter.Serializable -> add(AddSerializableAnnotationInterceptor())
            }
            if (ConfigManager.parenClassTemplate.isNotBlank()) {
                add(ParentClassTemplateKotlinClassInterceptor())
            }
            if (ConfigManager.isCommentOff) {
                add(CommentOffInterceptor)
            }
            if (ConfigManager.isOrderByAlphabetical) {
                add(OrderPropertyByAlphabeticalInterceptor())
            }

        }.apply {
            //add extensions's interceptor
            addAll(ExtensionsCollector.extensions)
        }.apply {
            if (ConfigManager.enableMinimalAnnotation) {
                add(MinimalAnnotationKotlinClassInterceptor())
            }
            add(FinalKotlinClassWrapperInterceptor())
        }
    }


    fun getEnabledImportClassDeclarationInterceptors(): List<IImportClassDeclarationInterceptor> {
        return mutableListOf<IImportClassDeclarationInterceptor>().apply {
            when (ConfigManager.targetJsonConverterLib) {
                TargetJsonConverter.Gson->add(AddGsonAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.FastJson-> add(AddFastjsonAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.Jackson-> add(AddJacksonAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.MoShi->add(AddMoshiAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.MoshiCodeGen->add(AddMoshiCodeGenClassImportDeclarationInterceptor())
                TargetJsonConverter.LoganSquare->add(AddLoganSquareAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.Custom->add(AddCustomAnnotationClassImportDeclarationInterceptor())
                TargetJsonConverter.Serializable->add(AddSerializableAnnotationClassImportDeclarationInterceptor())
                else->{}
            }

            if (ConfigManager.parenClassTemplate.isNotBlank()) {
                add(ParentClassClassImportDeclarationInterceptor())
            }
        }.apply {
            //add extensions's interceptor
            addAll(ExtensionsCollector.extensions)
        }
    }
}
