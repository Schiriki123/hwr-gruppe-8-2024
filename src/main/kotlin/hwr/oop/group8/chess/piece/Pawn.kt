package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.SingleMove

class Pawn(override val color: Color, val boardInspector: BoardInspector) :
  Piece {
  fun getPosition(): Position = boardInspector.findPositionOfPiece(this)

  override fun getValidMoveDestinations(): Set<SingleMove> {
    val validSingleMoves: MutableSet<SingleMove> = mutableSetOf()
    val forwardDirection: Direction
    val startRank: Rank
    val currentPosition = boardInspector.findPositionOfPiece(this)

    if (color == Color.WHITE) {
      forwardDirection = Direction.TOP
      startRank = Rank.TWO
    } else {
      forwardDirection = Direction.BOTTOM
      startRank = Rank.SEVEN
    }

    // Check for straight move
    val nextField = getPosition().nextPosition(forwardDirection)
    if (boardInspector.getPieceAt(nextField) == null) {
      validSingleMoves.add(SingleMove(currentPosition, nextField))
      // Check for double move from starting position
      if (getPosition().rank == startRank) {
        val twoSquaresForward = nextField.nextPosition(forwardDirection)
        if (boardInspector.getPieceAt(twoSquaresForward) == null) {
          validSingleMoves.add(SingleMove(currentPosition, twoSquaresForward))
        }
      }
    }

    // Check for diagonal captures
    for (direction in setOf(
      Direction.LEFT,
      Direction.RIGHT,
    )) {
      if (getPosition().hasNextPosition(direction)) {
        val nextPosition =
          getPosition().nextPosition(direction).nextPosition(forwardDirection)
        val nextPiece = boardInspector.getPieceAt(nextPosition)
        if (nextPiece != null && nextPiece.color != color) {
          validSingleMoves.add(SingleMove(currentPosition, nextPosition))
        }
      }
    }
    return validSingleMoves.toSet()
  }

  override fun moveCallback(move: SingleMove) {
    if (move.to.rank == Rank.EIGHT || move.to.rank == Rank.ONE) {
      requireNotNull(move.promotionChar)
      boardInspector.getSquare(move.to)
        .setPiece(promotion(move.promotionChar!!))
    }
    boardInspector.resetHalfMoveClock()
  }

  fun promotion(promoteTo: Char): Piece = when (promoteTo.lowercaseChar()) {
    'q' -> Queen(color, boardInspector)
    'r' -> Rook(color, boardInspector)
    'b' -> Bishop(color, boardInspector)
    'n' -> Knight(color, boardInspector)
    else -> throw IllegalArgumentException(
      "Invalid promotion piece: $promoteTo",
    )
  }

  override fun getChar(): Char = when (color) {
    Color.WHITE -> 'P'
    Color.BLACK -> 'p'
  }

  override fun getType(): PieceType = PieceType.PAWN
}
