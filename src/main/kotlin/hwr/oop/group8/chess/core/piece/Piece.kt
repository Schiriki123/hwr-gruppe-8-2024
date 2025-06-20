package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.move.Move

interface Piece {
  val color: Color // TODO: Move to genColor

  fun getValidMove(): Set<Move>
  fun fenRepresentation(): Char
  fun getType(): PieceType
}
