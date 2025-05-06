package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.BoardInspector
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Move
import kotlin.math.abs

class Knight(
    override val color: Color,
    override val boardInspector: BoardInspector,
) : Piece {
  override fun isMoveValid(move: Move, board: Board): Boolean {
    val from = move.from
    val to = move.to
    /*
        val direction = listOf(
          listOf(Direction.TOP, Direction.TOP_RIGHT),
          listOf(Direction.TOP, Direction.TOP_LEFT),
          listOf(Direction.BOTTOM, Direction.BOTTOM_RIGHT),
          listOf(Direction.BOTTOM, Direction.BOTTOM_LEFT),
          listOf(Direction.RIGHT, Direction.BOTTOM_RIGHT),
          listOf(Direction.RIGHT, Direction.TOP_RIGHT),
          listOf(Direction.LEFT, Direction.BOTTOM_LEFT),
          listOf(Direction.LEFT, Direction.TOP_LEFT)
        )
        // Kombination von moves als ein Path bzw eine Strecke speichern
    */

    if (!((abs(to.file - from.file) == 2 && abs(to.rank - from.rank) == 1) || (abs(
        to.file - from.file
      ) == 1 && abs(
        to.rank - from.rank
      ) == 2))
    )
      return false
    val targetPiece = board.getSquare(to).getPiece()
    // Check if the target square is empty or occupied by an opponent's piece
    return targetPiece == null || targetPiece.color != color
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'N'
      Color.BLACK -> 'n'
    }
  }
}
