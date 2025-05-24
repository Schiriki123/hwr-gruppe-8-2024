package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.*

class King(
  override val color: Color,
  val boardInspector: BoardInspector,
  override val moveHistory: MutableList<Move> = mutableListOf(),
) : Piece {
  override fun getValidMoveDestinations(): Set<Move> {
    val validMoves: MutableSet<Move> = mutableSetOf()
    val directions = Direction.entries // All possible directions

    val currentPosition = boardInspector.findPositionOfPiece(this)
    for (dir in directions) {
      if (currentPosition.hasNextPosition(dir)) {
        val nextPosition = currentPosition.nextPosition(dir)
        val nextPiece = boardInspector.getPieceAt(nextPosition)

        // Check if the next position is empty or occupied by an opponent's piece
        if (nextPiece == null || nextPiece.color != color) {
          validMoves.add(Move(currentPosition, nextPosition))
        }
      }
    }

    if (boardInspector.getCurrentTurn() == color) {
      val castling = boardInspector.isCastlingAllowed(color)
      val homeRank = if (color == Color.WHITE) 1 else 8
      val queenCastlePosition = Position('c', homeRank)
      val kingCastlePosition = Position('g', homeRank)

      val rookQueenSidePosition = Position('a', homeRank)
      val rookKingSidePosition = Position('h', homeRank)

      if (castling.first) {
        validMoves.add(
          Move(
            currentPosition, queenCastlePosition,
            listOf(
              Move(
                rookQueenSidePosition,
                currentPosition.nextPosition(Direction.LEFT)
              )
            )
          )
        )
      }
      if (castling.second) {
        validMoves.add(
          Move(
            currentPosition, kingCastlePosition,
            listOf(
              Move(
                rookKingSidePosition,
                currentPosition.nextPosition(Direction.RIGHT)
              )
            )
          )
        )
      }
    }
    return validMoves.toSet()
  }

  override fun saveMoveToHistory(move: Move) {
    moveHistory.add(move)
  }

  override fun getChar(): Char {
    return when (color) {
      Color.WHITE -> 'K'
      Color.BLACK -> 'k'
    }
  }
}
