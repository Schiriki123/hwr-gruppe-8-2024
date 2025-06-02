package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.piece.Piece

interface BoardInspector {
  fun getPieceAt(position: Position): Piece?
  fun isSquareEmpty(position: Position): Boolean = getPieceAt(position) == null
  fun findPositionOfPiece(piece: Piece): Position
  fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean>
  fun getCurrentTurn(): Color
}
