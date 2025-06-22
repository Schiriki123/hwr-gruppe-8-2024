package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.move.Move

interface Piece {
  fun color(): Color
  fun validMoves(): Set<Move>
  fun fenRepresentation(): Char
  fun pieceType(): PieceType
}
