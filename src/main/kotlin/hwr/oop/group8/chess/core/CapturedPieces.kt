package hwr.oop.group8.chess.core

import kotlin.text.indexOf

class CapturedPieces(val map: HashMap<Position, Square>) {

  fun getCapturedPieces(): String {
    val whitePieces = StringBuilder()
    whitePieces.append("RNBQKBNR")
    whitePieces.append("PPPPPPPP")
    val blackPieces = StringBuilder()
    blackPieces.append("rnbqkbnr")
    blackPieces.append("pppppppp")
    for (square in map.values) {
      square.getPiece()?.let { piece ->
        if (piece.color == Color.WHITE) {
          whitePieces.deleteAt(whitePieces.indexOf(piece.getChar()))
        } else {
          blackPieces.deleteAt(blackPieces.indexOf(piece.getChar()))
        }
      }
    }
    return "White's captures: $blackPieces${System.lineSeparator()}" +
      "Black's captures: $whitePieces"
  }
}
