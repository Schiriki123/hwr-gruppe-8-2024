package hwr.oop.group8.chess

import hwr.oop.group8.chess.piece.Piece

interface BoardInspector {
  fun getPieceAt(position: Position): Piece?
  fun findPositionOfPiece(piece: Piece): Position
  fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean>
  fun getCurrentTurn(): Color
}
