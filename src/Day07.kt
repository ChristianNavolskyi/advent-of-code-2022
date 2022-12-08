class Day07 : Challenge<Int> {
    override val name: String
        get() = "Day 07"

    override fun inputName(): String = "Day07"

    override fun testInputName(): String = "Day07_test"

    override fun testResult1(): Int = 95437

    override fun testResult2(): Int =24933642

    override fun part1(input: String): Int {
        val rootFolder = parseFolderStructure(input)
        val folderSizes: MutableList<Int> = mutableListOf()

        rootFolder.forEach {
            folderSizes.add(it.size)
        }

        return folderSizes.filter { it <= 100000 }.sum()
    }

    override fun part2(input: String): Int {
        val rootFolder = parseFolderStructure(input)
        val diskSize = 70000000
        val updateSize = 30000000
        val freeSpace = diskSize - rootFolder.size
        val spaceToBeFreed = updateSize - freeSpace

        val folderSizes: MutableList<Int> = mutableListOf()

        rootFolder.forEach {
            folderSizes.add(it.size)
        }

        folderSizes.sort()

        return folderSizes.first { it >= spaceToBeFreed }
    }

    private fun parseFolderStructure(input: String): Folder {
        val inputLines = input.substring(1).split("$").map { it.trim() }
        val rootFolder = Folder("/", null)

        var currentDirectory = rootFolder

        inputLines.subList(1, inputLines.size).forEach {
            when (it.substring(0, 2)) {
                "cd" -> currentDirectory = currentDirectory.applyCommand(CDCommand(it.split(" ")[1]))
                "ls" -> currentDirectory = currentDirectory.applyCommand(LSCommand(it.substring(3).split("\n")))
            }
        }

        return rootFolder
    }
}

interface FilesChangedNotifier {
    fun fileAdded(fileSize: Int)
}

class Folder(val name: String, val parent: Folder?) : FilesChangedNotifier {
    private val folders: MutableList<Folder> = mutableListOf()
    private val files: MutableList<File> = mutableListOf()
    private var fileSize: Int = 0
    private var childrenFileSize: Int = 0

    val size: Int
        get() = fileSize + childrenFileSize


    fun addFile(file: File) {
        files.add(file)
        fileSize += file.size
        parent?.fileAdded(file.size)
    }

    fun addFolder(folder: Folder) = folders.add(folder)

    fun getFolder(name: String): Folder? = folders.firstOrNull { it.name == name }

    fun applyCommand(commands: Command): Folder = commands.execute(this)

    fun forEach(function: (folder: Folder) -> Unit) {
        function(this)
        folders.forEach { it.forEach(function) }
    }

    fun printHierarchy(depth: Int = 0) {
        repeat(depth) { print("  ") }
        println("- $name (dir)")

        files.forEach {
            repeat(depth) { print("  ") }
            it.printFile()
        }

        folders.forEach { it.printHierarchy(depth + 1) }
    }

    override fun fileAdded(fileSize: Int) {
        childrenFileSize += fileSize
        parent?.fileAdded(fileSize)
    }
}

class File(val name: String, val size: Int) {
    fun printFile() {
        println("  - $name (file, size=$size)")
    }
}

abstract class Command {
    abstract fun execute(currentDirectory: Folder): Folder
}

class CDCommand(private val input: String) : Command() {
    override fun execute(currentDirectory: Folder): Folder {
        return when (input) {
            ".." -> {
                currentDirectory.parent ?: currentDirectory
            }

            "/" -> {
                var dir: Folder = currentDirectory

                repeat(10000) {
                    val parent = dir.parent

                    if (parent == null) {
                        return dir
                    } else {
                        dir = parent
                    }
                }

                currentDirectory
            }

            else -> {
                currentDirectory.getFolder(input)!!
            }
        }
    }
}

class LSCommand(private val output: List<String>) : Command() {
    override fun execute(currentDirectory: Folder): Folder {
        output.forEach {
            val outputParts: List<String> = it.split(" ")

            if (outputParts[0] == "dir") {
                currentDirectory.addFolder(Folder(outputParts[1], currentDirectory))
            } else {
                currentDirectory.addFile(File(outputParts[1], outputParts[0].toInt()))
            }
        }

        return currentDirectory
    }

}