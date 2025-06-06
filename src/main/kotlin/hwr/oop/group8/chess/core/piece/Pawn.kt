package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.move.DoublePawnMove
import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.move.PromotionMove
import hwr.oop.group8.chess.core.move.SingleMove

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
    val promotionRank = if (color == Color.WHITE) Rank.EIGHT else Rank.ONE

    moveGeneration(
      forwardDirection,
      validMoves,
      currentPosition,
      startRank,
      promotionRank,
    )

    return validMoves.toSet()
  }

  private fun moveGeneration(
    forwardDirection: Direction,
    validMoves: MutableSet<Move>,
    currentPosition: Position,
    startRank: Rank,
    promotionRank: Rank,
  ) {
    val nextField = getPosition().nextPosition(forwardDirection)
    if (boardInspector.isSquareEmpty(nextField)) {
      if (nextField.rank == promotionRank) {
        validMoves.addAll(generatePromotionMove(currentPosition, nextField))
      } else {
        validMoves.add(SingleMove(currentPosition, nextField))
      }
      // Check for double move from starting position
      if (getPosition().rank == startRank) {
        val twoSquaresForward = nextField.nextPosition(forwardDirection)
        if (boardInspector.isSquareEmpty(twoSquaresForward)) {
          validMoves.add(DoublePawnMove(currentPosition, twoSquaresForward))
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
          if (nextField.rank == promotionRank) {
            validMoves.addAll(
              generatePromotionMove(
                currentPosition,
                nextPosition,
              ),
            )
          } else {
            validMoves.add(SingleMove(currentPosition, nextPosition))
          }
        }
      }
    }
  }

  private fun generatePromotionMove(from: Position, to: Position): Set<Move> =
    listOf(
      PieceType.QUEEN,
      PieceType.ROOK,
      PieceType.BISHOP,
      PieceType.KNIGHT,
    ).map { promotionType -> PromotionMove(from, to, promotionType) }.toSet()

  override fun getChar(): Char = when (color) {
    Color.WHITE -> 'P'
    Color.BLACK -> 'p'
  }

  override fun getType(): PieceType = PieceType.PAWN
}
