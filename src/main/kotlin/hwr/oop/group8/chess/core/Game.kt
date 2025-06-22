package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.cli.CliMove
import hwr.oop.group8.chess.persistence.FEN

data class Game(
  val id: Int,
  private val fen: FEN,
  private val stateHistory: List<Int> = listOf(),
) {
  val board: Board = Board.factory(fen, stateHistory)

  fun getFen(): FEN = FEN.getFEN(board)
  fun makeMove(move: CliMove) {
    board.makeMove(move.toDomainMove())
  }
}
