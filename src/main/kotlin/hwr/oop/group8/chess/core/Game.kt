package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.cli.CliMove
import hwr.oop.group8.chess.persistence.FENData

data class Game(
  val id: Int,
  private val fenData: FENData,
  private val stateHistory: List<Int> = listOf(fenData.hashOfBoard()),
) {
  val board: Board = Board(fenData, stateHistory.toMutableList())

  fun getFenData(): FENData = FENData.getFENData(board)
  fun makeMove(move: CliMove) {
    board.makeMove(move.toDomainMove())
  }
}
