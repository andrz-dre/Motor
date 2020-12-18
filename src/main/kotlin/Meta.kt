import com.google.gson.JsonElement

data class Meta(
    val title : MetaContent
)

data class MetaContent(
    val element: String,
    val content: JsonElement
)