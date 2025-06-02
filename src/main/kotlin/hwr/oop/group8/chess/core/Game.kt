package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.persistence.FENData

data class Game(
  val id: Int,
  private val fenData: FENData,
  private val moveHistory: List<Int> = listOf(fenData.boardString.hashCode()),
) {
  val board: Board = Board(fenData, moveHistory.toMutableList())

  fun getFenData(): FENData = board.getFENData()
  fun makeMove(move: Move) {
    board.makeMove(move)
  }
}
