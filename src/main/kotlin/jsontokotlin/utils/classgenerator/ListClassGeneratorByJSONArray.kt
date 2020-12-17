package jsontokotlin.utils.classgenerator

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.classscodestruct.ListClass
import jsontokotlin.utils.BACKSTAGE_NULLABLE_POSTFIX
import jsontokotlin.utils.allElementAreSamePrimitiveType
import jsontokotlin.utils.allItemAreArrayElement
import jsontokotlin.utils.allItemAreNullElement
import jsontokotlin.utils.allItemAreObjectElement
import jsontokotlin.utils.toKotlinClass

/**
 * Created by Seal.Wu on 2019-11-20
 * Generate List Class from JsonArray String
 */
class ListClassGeneratorByJSONArray(private val className: String, jsonArrayString: String) {

    private val tag = "ListClassGeneratorByJSONArray"
    private val jsonArray: JsonArray = Gson().fromJson(jsonArrayString, JsonArray::class.java)

    fun generate(): ListClass {

        when {
            jsonArray.size() == 0 -> {
                return ListClass(name = className, generic = KotlinClass.ANY)
            }
            jsonArray.allItemAreNullElement() -> {
                return ListClass(name = className, generic = KotlinClass.ANY)
            }
            jsonArray.allElementAreSamePrimitiveType() -> {
                val elementKotlinClass = jsonArray[0].asJsonPrimitive.toKotlinClass()
                return ListClass(name = className, generic = elementKotlinClass)
            }
            jsonArray.allItemAreObjectElement() -> {
                val fatJsonObject = getFatJsonObject(jsonArray)
                val itemObjClassName = "${className}Item"
                val dataClassFromJsonObj = DataClassGeneratorByJSONObject(itemObjClassName, fatJsonObject).generate()
                return ListClass(className, dataClassFromJsonObj)
            }
            jsonArray.allItemAreArrayElement() -> {
                val fatJsonArray = getFatJsonArray(jsonArray)
                val itemArrayClassName = "${className}SubList"
                val listClassFromFatJsonArray = ListClassGeneratorByJSONArray(itemArrayClassName, fatJsonArray.toString()).generate()
                return ListClass(className, listClassFromFatJsonArray)
            }
            else -> {
                return ListClass(name = className, generic = KotlinClass.ANY)
            }
        }
    }

    private fun getFatJsonArray(jsonArray: JsonArray): JsonArray {
        if (jsonArray.size() == 0 || !jsonArray.allItemAreArrayElement()) {
            throw IllegalStateException("input arg jsonArray must not be empty and all element should be json array! ")
        }
        val fatJsonArray = JsonArray()
        jsonArray.forEach {
            fatJsonArray.addAll(it.asJsonArray)
        }
        return fatJsonArray
    }


    /**
     * get a Fat JsonObject whose fields contains all the objects' fields around the objects of the json array
     */
    private fun getFatJsonObject(jsonArray: JsonArray): JsonObject {
        if (jsonArray.size() == 0 || !jsonArray.allItemAreObjectElement()) {
            throw IllegalStateException("input arg jsonArray must not be empty and all element should be json object! ")
        }
        val allFields = jsonArray.flatMap { it.asJsonObject.entrySet().map { entry -> Pair(entry.key, entry.value) } }
        val fatJsonObject = JsonObject()
        allFields.forEach { (key, value) ->
            if (value is JsonNull && fatJsonObject.has(key)) {
                //if the value is null and pre added the same key into the fatJsonObject,
                // then translate it to a new special property to indicate that the property is nullable
                //later will consume this property (do it here[DataClassGeneratorByJSONObject#consumeBackstageProperties])
                // delete it or translate it back to normal property without [BACKSTAGE_NULLABLE_POSTFIX] when consume it
                // and will not be generated in final code
                fatJsonObject.add(key + BACKSTAGE_NULLABLE_POSTFIX, value)
            } else {
                fatJsonObject.add(key, value)
            }
        }
        return fatJsonObject
    }

}
