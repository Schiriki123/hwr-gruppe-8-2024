package hwr.oop.group8.chess

data class Position(val file: Char, val rank: Int) {
  init {
    require(file in 'a'..'h') { "File must be between 'a' and 'h'" }
    require(rank in 1..8) { "Rank must be between 1 and 8" }
  }

  @Deprecated("Use direction.nextPosition() instead")
  fun getAdjacentPosition(direction: Direction): Position {
    return when (direction) {
      Direction.TOP -> Position(file, rank + 1)
      Direction.BOTTOM -> Position(file, rank - 1)
      Direction.LEFT -> Position(file - 1, rank)
      Direction.RIGHT -> Position(file + 1, rank)
      Direction.TOP_LEFT -> Position(file - 1, rank + 1)
      Direction.TOP_RIGHT -> Position(file + 1, rank + 1)
      Direction.BOTTOM_LEFT -> Position(file - 1, rank - 1)
      Direction.BOTTOM_RIGHT -> Position(file + 1, rank - 1)
    }
  }

  override fun toString(): String {
    return "$file$rank"
  }
}
