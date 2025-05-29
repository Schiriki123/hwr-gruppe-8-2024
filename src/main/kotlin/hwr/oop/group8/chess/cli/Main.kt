package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.FileSystemAdapter
import java.io.File

fun main(args: Array<String>) {
  val storageFile = File("games.txt")
  storageFile.createNewFile()
  val fileSystemAdapter =
    FileSystemAdapter(storageFile)
  val cli =
    Cli(
      fileSystemAdapter,
      fileSystemAdapter,
    )
  cli.handle(args.toList())
}
