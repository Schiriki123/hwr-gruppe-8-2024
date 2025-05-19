package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.GamePersistenceAdapter
import java.io.File

fun main(args: Array<String>) {
  val storageFile = File("games.txt")
  storageFile.createNewFile()
  val gamePersistenceAdapter =
    GamePersistenceAdapter(storageFile)
  val cli =
    Cli(
      gamePersistenceAdapter,
      gamePersistenceAdapter,
      gamePersistenceAdapter,
    )
  cli.handle(args.toList())
}
