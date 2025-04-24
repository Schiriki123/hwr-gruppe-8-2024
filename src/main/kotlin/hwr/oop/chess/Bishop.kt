package hwr.oop.chess

import kotlin.math.abs

data class Bishop(
  override val isWhite: Boolean,
  override val isCaptured: Boolean = false,
) : Piece {
  override fun move(startPosition: Position): Set<Position> {
    val possibleMoves = mutableSetOf<Position>()

    for (rank in 1..8) {
      for (file in 'a'..'h') {
        // Check if the position is a diagonal move
        if (abs(startPosition.file - file) == abs(startPosition.rank - rank)) {
          // Exclude the current position
          if (file != startPosition.file && rank != startPosition.rank) {
            possibleMoves.add(Position(file, rank))
          }
        }
      }
    }
    return possibleMoves
  }
}
