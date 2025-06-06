package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.move.Move

interface Piece {
  val color: Color // TODO: Move to genColor

  // Valid, Possible or Potential Moves
  fun getValidMoveDestinations(): Set<Move> // TODO: Rename to getValidMoves
  fun getChar(): Char // TODO: Extract to FEN
  fun getType(): PieceType
}
