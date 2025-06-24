package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import hwr.oop.group8.chess.core.IllegalMoveException
import hwr.oop.group8.chess.persistence.FilePersistenceAdapter
import hwr.oop.group8.chess.persistence.PersistenceException
import java.io.File

class Chess : CliktCommand() {
  init {
    val storageFile = File("games.csv")
    storageFile.createNewFile()
    val persistenceAdapter = FilePersistenceAdapter(storageFile)
    subcommands(
      NewGame(persistenceAdapter),
      MakeMove(persistenceAdapter),
      ShowGame(persistenceAdapter),
      ListGames(persistenceAdapter),
      DeleteGame(persistenceAdapter),
    )
  }

  override fun run() = Unit
}

fun main(args: Array<String>) = try {
  Chess().main(args)
} catch (e: IllegalMoveException) {
  println("ILLEGAL MOVE: ${e.message}")
} catch (e: PersistenceException) {
  println("INVALID ID: ${e.message}")
}
