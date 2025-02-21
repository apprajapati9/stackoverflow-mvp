import java.io.File
import java.io.FileInputStream

fun main() {

    val file = File(".")

    printAllFiles(file)

    val readFile = File("local.properties")

    val inputStream = FileInputStream(readFile) //Get the input stream first

    val bytes = inputStream.readBytes() //read byes from the stream

    inputStream.close() // remember to close input stream

    println(bytes.decodeToString())


    //Another way to read file, use extension function also closes input stream after done reading
    println()
    println("Using use{} and readBytes() ----")
    println()
    val fileContents = FileInputStream(readFile).use {
        it.readBytes()
    }

    println(fileContents.decodeToString())

    //// More lower level instead of using readBytes()
    println()
    println("Using read()----")
    println()

    val stringBuilder = StringBuilder()

    FileInputStream(readFile).use { stream ->
        var byte = stream.read()
        while (byte != -1) {
            stringBuilder.append(byte.toChar())
            byte = stream.read()
        }
    }
    println(stringBuilder.toString())
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
            append("|_ ${file.name}")
        }
        println(line)

        if (file.isDirectory) {
            printAllFiles(file, indentationLevel = indentationLevel + 1)
        }
    }

}