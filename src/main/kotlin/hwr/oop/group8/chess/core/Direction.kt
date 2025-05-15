package hwr.oop.group8.chess.core

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

  fun hasNextPosition(position: Position): Boolean {
    return position.file + fileShift in 'a'..'h' && position.rank + rankShift in 1..8
  }

  fun combine(other: Direction): Direction {
    val combinedFileShift = this.fileShift + other.fileShift
    val combinedRankShift = this.rankShift + other.rankShift
    return entries.firstOrNull { it.fileShift == combinedFileShift && it.rankShift == combinedRankShift }
      ?: throw IllegalArgumentException("Invalid direction combination: $this and $other")
  }
}
