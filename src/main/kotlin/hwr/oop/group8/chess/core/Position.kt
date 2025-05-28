package hwr.oop.group8.chess.core

data class Position(val file: File, val rank: Rank) {
  override fun toString(): String = "$file$rank"

  fun nextPosition(direction: Direction): Position = Position(
    File.entries[file.value + direction.fileShift],
    Rank.entries[rank.value + direction.rankShift],
  )

  fun hasNextPosition(direction: Direction): Boolean =
    File.entries.getOrNull(file.value + direction.fileShift) != null &&
      Rank.entries.getOrNull(rank.value + direction.rankShift) != null
}
