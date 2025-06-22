package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Game
import java.io.File

class FilePersistenceAdapter(val file: File) : PersistencePort {
  override fun saveGame(game: Game, updateExistingGame: Boolean) {
    val lines = file.readLines()
    val gameFenString = game.fen().toString()
    val stateHistory = game.board.newStateHistory().joinToString(" ")
    val gameLineContent = "${game.id},$gameFenString,$stateHistory"
    val updatedLines: List<String>

    if (updateExistingGame) {
      if (!lines.any { it.startsWith("${game.id}") }) {
        throw CouldNotSaveGameException(
          "Game with id ${game.id} does not exist",
        )
      }

      updatedLines = lines.map { lines ->
        if (lines.startsWith("${game.id}")) {
          gameLineContent
        } else {
          lines
        }
      }
    } else {
      if (lines.any { it.startsWith("${game.id}") }) {
        throw CouldNotSaveGameException(
          "Game with id ${game.id} already exists",
        )
      }
      updatedLines = lines + gameLineContent
    }

    file.writeText(updatedLines.joinToString("${System.lineSeparator()}"))
  }

  override fun deleteGame(id: Int) {
    val lines = file.readLines()
    val updatedLines = lines.filterNot { it.startsWith("$id,") }
    if (lines.size == updatedLines.size) {
      throw CouldNotDeleteGameException("Game with id $id does not exist")
    }
    file.writeText(updatedLines.joinToString("${System.lineSeparator()}"))
  }

  override fun loadAllGames(): List<Game> {
    val games: List<Triple<Int, String, String>> =
      file.readLines().map { line ->
        val parts = line.split(",")
        Triple(parts.first().toInt(), parts[1], parts[2])
      }
    return games.map { (id, fenString, history) ->
      Game(
        id,
        createFEN(fenString),
        history.split(" ").map { it.toInt() },
      )
    }
  }

  private fun createFEN(fenLine: String): FEN {
    val part = fenLine.split(" ")
    val boardString = part.first()
    val turn: Char = part[1].first()
    val castle: String = part[2]
    val enPassant: String = part[3]
    val halfmoveClock: Int = part[4].toInt()
    val fullmoveClock: Int = part[5].toInt()
    return FEN(
      boardString,
      turn,
      castle,
      enPassant,
      halfmoveClock,
      fullmoveClock,
    )
  }
}
