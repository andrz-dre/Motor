import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

object ApiElementParser {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun generateResponseAndRequest(apiElement: JsonObject, savePath: String) {
        println("Generating Response and Request")
        val listOfTransition: MutableList<Transition> = getTransitions(apiElement)
        listOfTransition.removeIf { it.element == "copy" }
        listOfTransition.forEach { transitionElement ->
            transitionElement.content?.forEach { transition ->
                val element = transition.asJsonObject.get("element")?.asString.orEmpty()

                if (element == "httpTransaction") {
                    val httpTransactionContent = transition.asJsonObject.get("content").asJsonArray

                    httpTransactionContent.forEach { httpTransaction ->
                        val httpTransactionObject = httpTransaction.asJsonObject
                        val httpElement = httpTransactionObject.get("element").asString.orEmpty()
                        val httpContent = httpTransactionObject.get("content").asJsonArray

                        httpContent.forEach { assetElement ->
                            val content = assetElement.asJsonObject.get("content")
                            val contentString = gson.fromJson(content, String::class.java)

                            val className = transitionElement.meta?.title?.content?.asString.orEmpty().replace(" ", "")
                            val suffix = if (httpElement == "httpRequest") {
                                "Request"
                            } else {
                                "Response"
                            }

                            if (contentString.isNotEmpty()) {
                                ModelGenerator().generate(
                                    ModelConfig(
                                        className,
                                        savePath,
                                        contentString
                                    ),
                                    suffix,
                                    false
                                )
                            }
                        }
                    }
                }
            }

            println("File has been generated")
        }
    }

    private fun getTransitions(apiElement: JsonObject): MutableList<Transition> {
        val transitions: MutableList<Transition> = mutableListOf()
        val parseResultContent = apiElement.get("content").asJsonArray
        parseResultContent?.get(0)?.let { categoryElement ->
            // get category content
            val categoryContent = categoryElement.asJsonObject.get("content").asJsonArray
            categoryContent?.forEach { resourceElement ->
                // get resource
                val element = resourceElement.asJsonObject.get("element")?.asString.orEmpty()
                if (element == "resource") {
                    // get resource content
                    val resourceContent = resourceElement.asJsonObject?.get("content")?.asJsonArray
                    val typeTransitionList = TypeToken.getParameterized(List::class.java, Transition::class.java).type
                    transitions.addAll(gson.fromJson(resourceContent, typeTransitionList))
                }
            }
        }

        return transitions
    }


}