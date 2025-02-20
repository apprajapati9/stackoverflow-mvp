import java.io.File

fun main() {

    val file = File(".")

    printAllFiles(file)
}

/*
    ajay/
        ajay2
        txt
        txt
        ajay3 txt
            doc
            ajay4
                txt
                txt
                txx
 */
fun printAllFiles(folder: File, indentationLevel: Int = 0) {
    folder.listFiles()?.forEach { file ->

        val line = buildString {
            repeat(indentationLevel) {
                append("  ")
            }
            append("- ${file.name}")
        }
        println(line)

        if (file.isDirectory) {
            printAllFiles(file, indentationLevel = indentationLevel + 1)
        }
    }

}