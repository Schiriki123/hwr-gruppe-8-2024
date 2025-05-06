package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Move

class King(override val color: Color,
           override val boardInspector: BoardInspector
) : Piece {
  override fun isMoveValid(move: Move, board: Board): Boolean {

    val from = move.from
    val to = move.to
    val direction = move.getMoveDirection()
    val nextField = from.getAdjacentPosition(direction)

    // Single square move
    if (to != nextField) {
      return false
    }
    return true
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'K'
      Color.BLACK -> 'k'
    }
  }
}
