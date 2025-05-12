package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.GamePersistenceAdapter
import java.io.File

fun main(args: Array<String>) {
  val gamePersistenceAdapter =
    GamePersistenceAdapter(File("src/main/resources/games"))
  val cli =
    Cli(
      gamePersistenceAdapter,
      gamePersistenceAdapter,
      gamePersistenceAdapter
    )
  cli.handle(args.toList())
}
