package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.piece.Piece

class Square(private var piece: Piece?) {
  fun getPiece(): Piece? {
    return piece
  }

  fun setPiece(piece: Piece?) {
    this.piece = piece
  }
}
