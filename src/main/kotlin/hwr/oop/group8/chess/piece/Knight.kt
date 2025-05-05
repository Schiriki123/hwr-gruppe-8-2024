package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.Direction
import hwr.oop.group8.chess.Move

class Knight(override val color: Color) : Piece {
  override fun isMoveValid(move: Move, board: Board): Boolean {
    var from = move.from
    val to = move.to
    val direction = move.getMoveDirection()
    from = from.getAdjacentPosition(direction)
    listOf(
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


    return true
  }

  override fun getChar(): Char {
    return when(color){
      Color.WHITE -> 'N'
      Color.BLACK -> 'n'
    }
  }
}
