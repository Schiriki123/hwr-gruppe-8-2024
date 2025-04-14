package hwr.oop.chess

interface Piece {
  val isWhite: Boolean
  val isCaptured: Boolean
  fun move(position: Position): Set<Position>
}
