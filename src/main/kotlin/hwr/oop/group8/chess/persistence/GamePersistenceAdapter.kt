package hwr.oop.group8.chess.persistence

import java.io.File

class GamePersistenceAdapter(val file: File, val id: Int) {
  fun load(): FENData {
    val games: List<Pair<Int, String>> = file.readLines().map { line ->
      val parts = line.split(",")
      Pair(parts.first().toInt(), parts.last())
    }
    val data = games.find { it.first == id }?.second
      ?: throw CouldNotLoadGameException("Could not load game with id $id")
    return createFENDataObject(data)
  }

  fun save(data: FENData) {
    val lines = file.readLines()
    val gameFenString = data.toString()
    val gameLineContent = "$id,$gameFenString"

    val updatedLines = if (lines.any { it.startsWith("$id") }) {
      lines.map { lines ->
        if (lines.startsWith("$id")) {
          gameLineContent
        } else {
          lines
        }
      }
    } else {
      throw CouldNotSaveGameException("Game with id $id does not exist")
    }

    file.writeText(updatedLines.joinToString("\n"))
  }

  fun initGame(fenData: FENData = FENData()) {
    val lines = file.readLines()
    val gameFenString = fenData.toString()
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
