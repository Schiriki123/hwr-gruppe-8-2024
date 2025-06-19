package hwr.oop.group8.chess.core

data class Position(val file: File, val rank: Rank) {
  override fun toString(): String = "$file$rank"

  fun left(): Position = Position(file.left(), rank)
  fun right(): Position = Position(file.right(), rank)
  fun up(): Position = Position(file, rank.up())
  fun down(): Position = Position(file, rank.down())
  fun upLeft(): Position = Position(file.left(), rank.up())
  fun upRight(): Position = Position(file.right(), rank.up())
  fun downLeft(): Position = Position(file.left(), rank.down())
  fun downRight(): Position = Position(file.right(), rank.down())

  fun nextPosition(direction: Direction) = when (direction) {
    Direction.TOP -> up()
    Direction.BOTTOM -> down()
    Direction.LEFT -> left()
    Direction.RIGHT -> right()
    Direction.TOP_LEFT -> upLeft()
    Direction.TOP_RIGHT -> upRight()
    Direction.BOTTOM_LEFT -> downLeft()
    Direction.BOTTOM_RIGHT -> downRight()
  }

  fun hasNextPosition(direction: Direction): Boolean = when (direction) {
    Direction.TOP -> rank != Rank.EIGHT
    Direction.BOTTOM -> rank != Rank.ONE
    Direction.LEFT -> file != File.A
    Direction.RIGHT -> file != File.H
    Direction.TOP_LEFT -> rank != Rank.EIGHT && file != File.A
    Direction.TOP_RIGHT -> rank != Rank.EIGHT && file != File.H
    Direction.BOTTOM_LEFT -> rank != Rank.ONE && file != File.A
    Direction.BOTTOM_RIGHT -> rank != Rank.ONE && file != File.H
  }

  companion object {
    fun fromString(position: String): Position {
      if (position.length != 2) {
        throw IllegalArgumentException("Invalid position string: $position")
      }
      val file = File.fromChar(position.first())
      val rank = Rank.fromInt(position.last().digitToInt())
      return Position(file, rank)
    }
  }
}
