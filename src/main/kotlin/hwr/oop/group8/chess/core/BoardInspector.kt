package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.piece.Piece

interface BoardInspector {
  fun getPieceAt(position: Position): Piece?
  fun findPositionOfPiece(piece: Piece): Position
  fun isSquareEmpty(position: Position): Boolean
  fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean>
  fun getCurrentTurn(): Color
  fun isPositionThreatened(currentPlayer: Color, position: Position): Boolean
  fun resetHalfMoveClock()
}
