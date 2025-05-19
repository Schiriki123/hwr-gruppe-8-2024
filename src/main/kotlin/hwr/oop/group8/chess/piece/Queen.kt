package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Move

class Queen(
  override val color: Color,
  val boardInspector: BoardInspector,
) : Piece {
  override fun getValidMoveDestinations(): Set<Move> {
    val validMoves: MutableSet<Move> = mutableSetOf()
    val directions = Direction.entries
    val currentPosition = boardInspector.findPositionOfPiece(this)

    for (dir in directions) {
      var nextPosition = boardInspector.findPositionOfPiece(this)
      while (nextPosition.hasNextPosition(dir)) {
        nextPosition = nextPosition.nextPosition(dir)
        val nextPiece = boardInspector.getPieceAt(nextPosition)
        if (nextPiece == null) {
          validMoves.add(Move(currentPosition, nextPosition))
        } else if (nextPiece.color != color) {
          validMoves.add(Move(currentPosition, nextPosition))
          break
        } else {
          break
        }
      }
    }

    return validMoves.toSet()
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'Q'
      Color.BLACK -> 'q'
    }
  }
}
