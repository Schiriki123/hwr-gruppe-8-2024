package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.CastleMove
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.SingleMove

class King(override val color: Color, val boardInspector: BoardInspector) :
  Piece {
  override fun getValidMoveDestinations(): Set<Move> {
    val validSingleMoves: MutableSet<Move> = mutableSetOf()
    val directions = Direction.entries // All possible directions

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
      val homeRank = if (color == Color.WHITE) Rank.ONE else Rank.EIGHT

      if (castling.first) {
        validSingleMoves.add(CastleMove(this, false))
      }
      if (castling.second) {
        validSingleMoves.add(CastleMove(this, true))
      }
    }
    return validSingleMoves.toSet()
  }

  override fun moveCallback(move: SingleMove) {}

  override fun getChar(): Char = when (color) {
    Color.WHITE -> 'K'
    Color.BLACK -> 'k'
  }

  override fun getType(): PieceType = PieceType.KING
}
