package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.PromotionMove
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.SingleMove

class Pawn(override val color: Color, val boardInspector: BoardInspector) :
  Piece {
  fun getPosition(): Position = boardInspector.findPositionOfPiece(this)

  override fun getValidMoveDestinations(): Set<Move> {
    val validMoves: MutableSet<Move> = mutableSetOf()
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
    if (currentPosition.rank ==
      if (color == Color.WHITE) Rank.SEVEN else Rank.TWO
    ) {
      promotionMoveGeneration(
        forwardDirection,
        validMoves,
        currentPosition,
        startRank,
      )
    } else {
      moveGeneration(
        forwardDirection,
        validMoves,
        currentPosition,
        startRank,
      )
    }
    return validMoves.toSet()
  }

  private fun moveGeneration(
    forwardDirection: Direction,
    validSingleMoves: MutableSet<Move>,
    currentPosition: Position,
    startRank: Rank,
  ) {
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
  }

  private fun promotionMoveGeneration(
    forwardDirection: Direction,
    validSingleMoves: MutableSet<Move>,
    currentPosition: Position,
    startRank: Rank,
  ) {
    val nextField = getPosition().nextPosition(forwardDirection)
    if (boardInspector.getPieceAt(nextField) == null) {
      validSingleMoves.add(
        PromotionMove(
          currentPosition,
          nextField,
          PieceType.QUEEN,
        ),
      )
      // Check for double move from starting position
      if (getPosition().rank == startRank) {
        val twoSquaresForward = nextField.nextPosition(forwardDirection)
        if (boardInspector.getPieceAt(twoSquaresForward) == null) {
          validSingleMoves.add(
            PromotionMove(
              currentPosition,
              twoSquaresForward,
              PieceType.QUEEN,
            ),
          )
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
          validSingleMoves.add(
            PromotionMove(
              currentPosition,
              nextPosition,
              PieceType.QUEEN,
            ),
          )
        }
      }
    }
  }

  override fun getChar(): Char = when (color) {
    Color.WHITE -> 'P'
    Color.BLACK -> 'p'
  }

  override fun getType(): PieceType = PieceType.PAWN
}
