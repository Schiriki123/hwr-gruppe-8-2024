package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.piece.Piece

interface BoardInspector {
  fun pieceAt(position: Position): Piece?
  fun isSquareEmpty(position: Position): Boolean = pieceAt(position) == null
  fun positionOfPiece(piece: Piece): Position
  fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean>
  fun currentTurn(): Color
  fun accessEnPassant(): Position?
}
