package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Direction
import hwr.oop.group8.chess.Move

class Pawn(override val color: Color) : Piece {
  override fun isMoveValid(move: Move, board: Board): Boolean {
    var from = move.from
    val to = move.to
    val direction = move.getMoveDirection()
    val forwardDirection =
      if (color == Color.WHITE) Direction.TOP else Direction.BOTTOM

    if (move.isMoveStraight()) {
      TODO("Implement pawn straight movement")
    } else if (move.isMoveDiagonal()) {
      TODO("Implement pawn diagonal movement")
    } else return false
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'P'
      Color.BLACK -> 'p'
    }
  }
}
