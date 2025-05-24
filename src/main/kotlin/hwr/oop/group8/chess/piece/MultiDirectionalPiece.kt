package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.*

class MultiDirectionalPiece(
  override val color: Color,
  val boardInspector: BoardInspector,
  val directions: Set<Direction>,
  val currentPosition: Position,
) : Piece {
  override fun getValidMoveDestinations(): Set<Move> {
    val validDestinations: MutableSet<Move> = mutableSetOf()
    for (dir in directions) {
      var nextPosition = currentPosition
      while (nextPosition.hasNextPosition(dir)) {
        nextPosition = nextPosition.nextPosition(dir)
        val nextPiece = boardInspector.getPieceAt(nextPosition)
        if (nextPiece == null) {
          validDestinations.add(Move(currentPosition, nextPosition))
        } else if (nextPiece.color != color) {
          validDestinations.add(Move(currentPosition, nextPosition))
          break
        } else {
          break
        }
      }
    }
    return validDestinations
  }

  override fun moveCallback(move: Move) {}

  override fun getChar(): Char {
    TODO("Not yet implemented")
  }
}

