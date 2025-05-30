package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.FilePersistenceAdapter
import java.io.File

fun main(args: Array<String>) {
  val storageFile = File("games.csv")
  storageFile.createNewFile()
  val filePersistenceAdapter =
    FilePersistenceAdapter(storageFile)
  val cli =
    Cli(
      filePersistenceAdapter,
    )
  cli.handle(args.toList())
}
