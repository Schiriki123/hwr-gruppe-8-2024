package hwr.oop.chess

abstract class Piece(val isWhite: Boolean, val eliminated: Boolean = false) {
  abstract fun move()
}
