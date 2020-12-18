import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class Transition(
    val element: String?,
    val meta: Meta?,
    val attributes: JsonObject?,
    val content: JsonArray?
)