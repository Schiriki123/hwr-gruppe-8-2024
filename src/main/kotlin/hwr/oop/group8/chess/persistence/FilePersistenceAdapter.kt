package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Game
import java.io.File

class FilePersistenceAdapter(val file: File) : PersistencePort {
  override fun saveGame(game: Game, updateExistingGame: Boolean) {
    val lines = file.readLines()
    val gameFenString = game.getFenData().toString()
    val stateHistory = game.board.stateHistory.joinToString(" ")
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
        createFENDataObject(fenString),
        history.split(" ").map { it.toInt() },
      )
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
      fullmoveClock,
    )
  }
}
