package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank

class King(override val color: Color, val boardInspector: BoardInspector) :
  Piece {
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
      val homeRank = if (color == Color.WHITE) Rank.ONE else Rank.EIGHT
      val queenCastlePosition = Position(File.C, homeRank)
      val kingCastlePosition = Position(File.G, homeRank)

      val rookQueenSidePosition = Position(File.A, homeRank)
      val rookKingSidePosition = Position(File.H, homeRank)

      if (castling.first) {
        validMoves.add(
          Move(
            currentPosition,
            queenCastlePosition,
            listOf(
              Move(
                rookQueenSidePosition,
                currentPosition.nextPosition(Direction.LEFT),
              ),
            ),
          ),
        )
      }
      if (castling.second) {
        validMoves.add(
          Move(
            currentPosition,
            kingCastlePosition,
            listOf(
              Move(
                rookKingSidePosition,
                currentPosition.nextPosition(Direction.RIGHT),
              ),
            ),
          ),
        )
      }
    }
    return validMoves.toSet()
  }

  override fun moveCallback(move: Move) {}

  override fun getChar(): Char = when (color) {
    Color.WHITE -> 'K'
    Color.BLACK -> 'k'
  }

  override fun getType(): PieceType = PieceType.KING
}
