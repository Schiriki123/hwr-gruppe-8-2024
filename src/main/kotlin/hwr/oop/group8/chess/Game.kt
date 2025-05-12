package hwr.oop.group8.chess

import hwr.oop.group8.chess.persistence.FENData

data class Game(val id: Int, private val fenData: FENData) {
  val board: Board = Board(fenData)

  fun getFenData(): FENData {
    return board.getFENData()
  }
}
