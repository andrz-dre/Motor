import jsontokotlin.JsonToKotlinBuilder
import jsontokotlin.model.TargetJsonConverter

fun main(args: Array<String>) {
    val json1 = """{ "programmers": [
                { "isFirstName": "Brett", "lastName":"McLaughlin", "email": "aaaa" },
                { "firstName": "Jason", "lastName":"Hunter", "email": "bbbb" },
                { "firstName": "Elliotte", "lastName":"Harold", "email": "cccc" }
                ],
                "authors": [
                { "firstName": null, "lastName": "Asimov", "genre": "science fiction" },
                { "firstName": "Tad", "lastName": "Williams", "genre": "fantasy" },
                { "firstName": "Frank", "lastName": "Peretti", "genre": "christian fiction" }
                ],
                "musicians": [
                { "firstName": "Eric", "lastName": "Clapton", "instrument": "guitar" },
                { "firstName": "Sergei", "lastName": "Rachmaninoff", "instrument": "piano" }
                ] } """

    val output = JsonToKotlinBuilder()
        .setAnnotationLib(TargetJsonConverter.Gson)
        .build(json1, "GlossResponse")

    println(output)
}