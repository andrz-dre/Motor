
import io.outfoxx.swiftpoet.BOOL
import io.outfoxx.swiftpoet.DOUBLE
import io.outfoxx.swiftpoet.FLOAT
import io.outfoxx.swiftpoet.INT
import io.outfoxx.swiftpoet.INT64
import io.outfoxx.swiftpoet.Modifier
import io.outfoxx.swiftpoet.PropertySpec
import io.outfoxx.swiftpoet.STRING
import io.outfoxx.swiftpoet.TypeName
import io.outfoxx.swiftpoet.TypeSpec
import io.outfoxx.swiftpoet.TypeVariableName
import java.util.Stack
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.GenericListClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.classscodestruct.Property

class SwiftClassCodeMaker(private val kotlinClass: KotlinClass) {

    fun makeSwiftClassCode(): List<TypeSpec> {
        val dataClass = kotlinClass as DataClass
        val result: MutableList<TypeSpec> = mutableListOf()
        val stack: Stack<Property> = Stack()
        makeSwiftClassCodeHelper(dataClass, result, stack)
        return result
    }

    private fun makeSwiftClassCodeHelper(kotlinClass: KotlinClass, result: MutableList<TypeSpec>, cache: Stack<Property>) {
        if (kotlinClass is DataClass) {
            val structBuilder: TypeSpec.Builder = TypeSpec.structBuilder(kotlinClass.name)
            kotlinClass.properties.forEach {
                if (it.typeObject is GenericListClass) {
                    structBuilder.addProperty(
                        PropertySpec.builder(
                            it.name,
                            TypeVariableName.Companion.typeVariable("[${it.typeObject.generic.name}]"),
                            Modifier.PUBLIC
                        )
                            .mutable(true)
                            .initializer("[]")
                            .build()
                    )
                } else {
                    structBuilder.addProperty(
                        PropertySpec.builder(it.name, mapFromKotlinClassToSwift(it.type), Modifier.PUBLIC)
                            .mutable(true)
                            .initializer(getDefaultValue(it.type))
                            .build()
                    )
                }

                if (it.typeObject is DataClass) {
                    cache.push(it)
                }
            }
            result.add(structBuilder.build())
            if (cache.isNotEmpty()) {
                makeSwiftClassCodeHelper(cache.pop().typeObject, result, cache)
            }
        }
    }

    private fun mapFromKotlinClassToSwift(type: String): TypeName {
        return when(type) {
            KotlinClass.STRING.name -> STRING
            KotlinClass.INT.name -> INT
            KotlinClass.FLOAT.name -> FLOAT
            KotlinClass.DOUBLE.name -> DOUBLE
            KotlinClass.LONG.name -> INT64
            KotlinClass.BOOLEAN.name -> BOOL
            else -> TypeVariableName.Companion.typeVariable(type)
        }
    }

    private fun getDefaultValue(type: String): String {
        return when(type) {
            KotlinClass.STRING.name -> "\"\""
            KotlinClass.INT.name -> "0"
            KotlinClass.FLOAT.name -> "0"
            KotlinClass.DOUBLE.name -> "0"
            KotlinClass.LONG.name -> "0"
            KotlinClass.BOOLEAN.name -> "false"
            else -> "$type()"
        }
    }
}