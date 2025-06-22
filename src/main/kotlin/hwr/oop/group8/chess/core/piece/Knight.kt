package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.move.SingleMove

class Knight(val color: Color, val boardInspector: BoardInspector) : Piece {
  override fun color(): Color = color
  override fun getValidMove(): Set<SingleMove> {
    val validDestinations: MutableSet<SingleMove> = mutableSetOf()
    val currentPosition = boardInspector.findPositionOfPiece(this)

    val possibleDestination = listOf(
      Pair(Direction.TOP_RIGHT, Direction.RIGHT),
      Pair(Direction.TOP_LEFT, Direction.LEFT),
      Pair(Direction.BOTTOM_RIGHT, Direction.RIGHT),
      Pair(Direction.BOTTOM_LEFT, Direction.LEFT),
      Pair(Direction.TOP_RIGHT, Direction.TOP),
      Pair(Direction.TOP_LEFT, Direction.TOP),
      Pair(Direction.BOTTOM_RIGHT, Direction.BOTTOM),
      Pair(Direction.BOTTOM_LEFT, Direction.BOTTOM),
    )
    for (pair in possibleDestination) {
      try {
        val newPosition =
          currentPosition.nextPosition(pair.first).nextPosition(pair.second)
        if (boardInspector.getPieceAt(newPosition)?.color() == color) {
          continue
        }
        validDestinations.add(SingleMove(currentPosition, newPosition))
      } catch (_: IndexOutOfBoundsException) {
        // Ignore out of bounds exceptions, as they indicate invalid moves
      }
    }
    return validDestinations
  }

  override fun fenRepresentation(): Char = when (color) {
    Color.WHITE -> 'N'
    Color.BLACK -> 'n'
  }

  override fun getType(): PieceType = PieceType.KNIGHT
}
