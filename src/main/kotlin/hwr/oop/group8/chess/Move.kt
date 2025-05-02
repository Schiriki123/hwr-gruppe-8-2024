package hwr.oop.group8.chess

import kotlin.math.abs


class Move(val from: Position, val to: Position) {
  fun getMoveDirection(): Direction {
    val fileDiff = to.file - from.file
    val rankDiff = to.rank - from.rank
    require(from != to) { "From and to positions must be different" }
    require(isMoveDiagonal() || isMoveStraight()){"Invalid move: Is not straight or diagonal"}

    return when {
      fileDiff > 0 && rankDiff > 0 -> Direction.TOP_RIGHT
      fileDiff > 0 && rankDiff < 0 -> Direction.BOTTOM_RIGHT
      fileDiff < 0 && rankDiff > 0 -> Direction.TOP_LEFT
      fileDiff < 0 && rankDiff < 0 -> Direction.BOTTOM_LEFT
      fileDiff > 0 -> Direction.RIGHT
      fileDiff < 0 -> Direction.LEFT
      rankDiff > 0 -> Direction.TOP
      else -> Direction.BOTTOM
    }
  }

  fun isMoveDiagonal(): Boolean {
    return abs(to.file - from.file) == abs(to.rank - from.rank)
  }

  fun isMoveStraight(): Boolean {
    return to.file == from.file || to.rank == from.rank
  }
}
