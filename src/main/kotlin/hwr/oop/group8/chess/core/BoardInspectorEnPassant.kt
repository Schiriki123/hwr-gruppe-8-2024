package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.piece.Piece

interface BoardInspectorEnPassant {
  fun getPieceAt(position: Position): Piece?
  fun getCurrentTurn(): Color
  fun setEnPassant(position: Position?)
}
