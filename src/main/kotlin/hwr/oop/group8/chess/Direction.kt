package hwr.oop.group8.chess

enum class Direction(private val fileShift: Int, private val rankShift: Int) {
  TOP(0, 1),
  BOTTOM(0, -1),
  LEFT(-1, 0),
  RIGHT(1, 0),
  TOP_LEFT(-1, 1),
  TOP_RIGHT(1, 1),
  BOTTOM_LEFT(-1, -1),
  BOTTOM_RIGHT(1, -1);

  fun nextPosition(position: Position): Position {
    return Position(position.file + fileShift, position.rank + rankShift)
  }
}
