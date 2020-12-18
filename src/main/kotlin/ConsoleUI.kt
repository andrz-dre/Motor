import java.io.File
import java.util.Scanner

class ConsoleUI {
    fun start() {
        val scan = Scanner(System.`in`)

        print("Please define json file path:")
        val filePath = scan.nextLine().trim()

        print("Please define where you want the file saved:")
        val saveLocation = scan.nextLine().trim()

        val reader = File(filePath).bufferedReader()
        val json = reader.use { it.readText() }

        ModelGenerator().generate(
            ModelConfig("Testing", saveLocation, json),
            false
        )
    }
}