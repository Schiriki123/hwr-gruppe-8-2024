package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.Game
import java.io.File

class GamePersistenceAdapter(val file: File) : InitGameInterface,
  LoadGameInterface, SaveGameInterface {
  override fun loadGame(id: Int): Game {
    val games: List<Pair<Int, String>> = file.readLines().map { line ->
      val parts = line.split(",")
      Pair(parts.first().toInt(), parts.last())
    }
    val data = games.find { it.first == id }?.second
      ?: throw CouldNotLoadGameException("Could not load game with id $id")
    return Game(id, createFENDataObject(data))
  }

  override fun saveGame(game: Game) {
    val lines = file.readLines()
    val gameFenString = game.getFenData().toString()
    val gameLineContent = "${game.id},$gameFenString"

    val updatedLines = if (lines.any { it.startsWith("${game.id}") }) {
      lines.map { lines ->
        if (lines.startsWith("${game.id}")) {
          gameLineContent
        } else {
          lines
        }
      }
    } else {
      throw CouldNotSaveGameException("Game with id ${game.id} does not exist")
    }

    file.writeText(updatedLines.joinToString("\n"))
  }

  override fun initGame(id: Int) {
    val lines = file.readLines()
    val gameFenString = FENData().toString()
    val gameLineContent = "$id,$gameFenString"

    if (lines.any { it.startsWith("$id") }) {
      throw CouldNotSaveGameException("Game with id $id already exists")
    } else {
      file.appendText(gameLineContent)
    }
  }

  private fun createFENDataObject(fenLine: String): FENData {
    val data = fenLine.split(" ")
    val boardString = data.first()
    val turn: Char = data[1].first()
    val castle: String = data[2]
    val enPassant: String = data[3]
    val halfmoveClock: Int = data[4].toInt()
    val fullmoveClock: Int = data[5].toInt()
    return FENData(
      boardString,
      turn,
      castle,
      enPassant,
      halfmoveClock,
      fullmoveClock
    )
  }
}
