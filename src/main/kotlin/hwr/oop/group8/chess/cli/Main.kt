package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import hwr.oop.group8.chess.persistence.FilePersistenceAdapter
import java.io.File

class Chess : CliktCommand() {
  init {
    val storageFile = File("games.csv")
    storageFile.createNewFile()
    val persistenceAdapter = FilePersistenceAdapter(storageFile)
    subcommands(NewGame(persistenceAdapter))
    subcommands(MakeMove(persistenceAdapter))
    subcommands(ShowGame(persistenceAdapter))
    subcommands(ListGames(persistenceAdapter))
    subcommands(DeleteGame(persistenceAdapter))
  }

  override fun run() = Unit
}

fun main(args: Array<String>) = Chess().main(args)
