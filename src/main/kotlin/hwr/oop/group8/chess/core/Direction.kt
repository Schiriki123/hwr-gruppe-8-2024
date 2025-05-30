package hwr.oop.group8.chess.core

enum class Direction {
  TOP,
  BOTTOM,
  LEFT,
  RIGHT,
  TOP_LEFT,
  TOP_RIGHT,
  BOTTOM_LEFT,
  BOTTOM_RIGHT,
  ;

  fun appliedOn(position: Position): Position = when (this) {
    TOP -> position.up()
    BOTTOM -> position.down()
    LEFT -> position.left()
    RIGHT -> position.right()
    TOP_LEFT -> position.upLeft()
    TOP_RIGHT -> position.upRight()
    BOTTOM_LEFT -> position.downLeft()
    BOTTOM_RIGHT -> position.downRight()
  }
}
