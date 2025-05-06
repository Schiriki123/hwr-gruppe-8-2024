package hwr.oop.group8.chess

import hwr.oop.group8.chess.piece.Piece

fun interface BoardInspector {
  fun getPieceAt(position: Position): Piece?
}
