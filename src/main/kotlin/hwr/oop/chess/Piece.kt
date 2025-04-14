package hwr.oop.chess

abstract class Piece(val isWhite: Boolean, val isCaptured: Boolean = false) {
  abstract fun move(): Set<Position>
}
