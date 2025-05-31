package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.cli.CliMove
import hwr.oop.group8.chess.persistence.FENData

data class Game(val id: Int, private val fenData: FENData) {
  val board: Board = Board(fenData)

  fun getFenData(): FENData = FENData.getFENData(board)
  fun makeMove(move: CliMove) {
    board.makeMove(move.toDomainMove())
  }
}
