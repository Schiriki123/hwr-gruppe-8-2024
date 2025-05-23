package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.piece.Piece

interface BoardInspector {
  fun getPieceAt(position: Position): Piece?
  fun findPositionOfPiece(piece: Piece): Position
  fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean>
  fun getCurrentTurn(): Color
}
