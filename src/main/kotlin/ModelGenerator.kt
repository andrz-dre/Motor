
import filegen.KotlinFileGen
import jsontokotlin.JsonToKotlinBuilder
import jsontokotlin.model.PropertyTypeStrategy
import jsontokotlin.model.TargetJsonConverter
/*
 json Location : /Users/ovo/JsonTesting/testing.json
* */
class ModelGenerator {
    fun generate(config: ModelConfig, isAndroidEnv: Boolean) {
        val output = JsonToKotlinBuilder()
            .setAnnotationLib(TargetJsonConverter.Gson)
            .setPropertyTypeStrategy(PropertyTypeStrategy.AutoDeterMineNullableOrNot)
            .enableKeepAnnotationOnClass(isAndroidEnv)
            .setClassSuffix("Response")
            .build(config.json, config.className)

        KotlinFileGen().also {
            it.generate("${config.className}.kt", output, config.path)
        }

        print("File has been generated")
    }
}