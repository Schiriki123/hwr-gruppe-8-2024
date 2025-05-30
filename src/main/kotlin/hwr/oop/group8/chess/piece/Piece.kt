package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.SingleMove

interface Piece {
  val color: Color
  fun getValidMoveDestinations(): Set<Move>
  fun moveCallback(move: SingleMove)
  fun getChar(): Char
  fun getType(): PieceType
}
