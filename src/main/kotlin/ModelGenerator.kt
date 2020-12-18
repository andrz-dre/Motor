
import filegen.KotlinFileGen
import io.outfoxx.swiftpoet.TypeSpec
import jsontokotlin.JsonToKotlinBuilder
import jsontokotlin.model.PropertyTypeStrategy
import jsontokotlin.model.TargetJsonConverter
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.utils.KotlinClassMaker

class ModelGenerator {
    fun generate(config: ModelConfig, suffix: String, isAndroidEnv: Boolean) {
        val output = JsonToKotlinBuilder()
            .setAnnotationLib(TargetJsonConverter.Gson)
            .setPropertyTypeStrategy(PropertyTypeStrategy.AutoDeterMineNullableOrNot)
            .enableKeepAnnotationOnClass(isAndroidEnv)
            .setClassSuffix(suffix)
            .build(config.json, config.className)

        KotlinFileGen().also {
            it.generate("${config.className}$suffix.kt", output, config.path)
        }

        val dataClass = KotlinClassMaker(config.className, config.json).makeKotlinClass() as DataClass
        val swiftClassCodeMaker = SwiftClassCodeMaker(dataClass)
        val result: List<TypeSpec> = swiftClassCodeMaker.makeSwiftClassCode()
        val builder = StringBuilder("import Foundation\n\n")
        result.forEach {
            builder.append(it.toString())
            builder.append("\n")
        }

        KotlinFileGen().also {
            it.generate("${config.className}$suffix.swift", builder.toString(), config.path)
        }
        print("File has been generated")
    }
}