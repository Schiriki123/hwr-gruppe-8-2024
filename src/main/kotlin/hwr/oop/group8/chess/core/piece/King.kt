package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.move.CastleMove
import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.move.SingleMove

class King(override val color: Color, val boardInspector: BoardInspector) :
  Piece {
  override fun getValidMove(): Set<Move> {
    val validSingleMoves: MutableSet<Move> = mutableSetOf()
    val directions = setOf(
      Direction.TOP,
      Direction.BOTTOM,
      Direction.LEFT,
      Direction.RIGHT,
      Direction.TOP_LEFT,
      Direction.TOP_RIGHT,
      Direction.BOTTOM_LEFT,
      Direction.BOTTOM_RIGHT,
    )

    val currentPosition = boardInspector.findPositionOfPiece(this)
    for (dir in directions) {
      if (currentPosition.hasNextPosition(dir)) {
        val nextPosition = currentPosition.nextPosition(dir)
        val nextPiece = boardInspector.getPieceAt(nextPosition)

        // Check if the next position is empty or occupied by an opponent's piece
        if (nextPiece == null || nextPiece.color != color) {
          validSingleMoves.add(SingleMove(currentPosition, nextPosition))
        }
      }
    }

    if (boardInspector.getCurrentTurn() == color) {
      val castling = boardInspector.isCastlingAllowed(color)

      if (castling.first) {
        validSingleMoves.add(CastleMove(this, false))
      }
      if (castling.second) {
        validSingleMoves.add(CastleMove(this, true))
      }
    }
    return validSingleMoves.toSet()
  }

  override fun toFENRepresentation(): Char = when (color) {
    Color.WHITE -> 'K'
    Color.BLACK -> 'k'
  }

  override fun getType(): PieceType = PieceType.KING
}
