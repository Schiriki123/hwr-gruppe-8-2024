package hwr.oop.group8.chess.core

data class SingleMove(val from: Position, val to: Position) : Move {
  override fun moves(): List<SingleMove> = listOf(this)
}
