package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Position

interface Piece {
  val color: Color
  fun getValidMoveDestinations(): Set<Position>
  fun getChar(): Char
}
