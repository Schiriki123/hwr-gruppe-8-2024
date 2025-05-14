package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Position

interface Piece {
  val color: Color
  fun getValidMoveDestinations(): Set<Position>
  fun getChar(): Char
}
