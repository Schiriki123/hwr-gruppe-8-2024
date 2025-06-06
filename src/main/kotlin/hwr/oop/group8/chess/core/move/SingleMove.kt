package hwr.oop.group8.chess.core.move

import hwr.oop.group8.chess.core.Position

data class SingleMove(val from: Position, val to: Position) : Move {
  override fun moves(): List<SingleMove> = listOf(this)
}
