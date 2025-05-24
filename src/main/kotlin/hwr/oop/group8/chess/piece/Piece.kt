package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Move

interface Piece {
  val color: Color
  val moveHistory: MutableList<Move>
  fun getValidMoveDestinations(): Set<Move>
  fun saveMoveToHistory(move: Move)
  fun getChar(): Char
}
