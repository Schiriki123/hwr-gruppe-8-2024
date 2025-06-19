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

  val startRank: Rank
  val forwardDirection: Direction
  val promotionRank: Rank

  init {
    when (color) {
      Color.WHITE -> {
        startRank = Rank.TWO
        forwardDirection = Direction.TOP
        promotionRank = Rank.EIGHT
      }

      Color.BLACK -> {
        startRank = Rank.SEVEN
        forwardDirection = Direction.BOTTOM
        promotionRank = Rank.ONE
      }
    }
  }

  override fun getValidMoveDestinations(): Set<Move> {
    val currentPosition = boardInspector.findPositionOfPiece(this)

    val validMoves: MutableSet<Move> = mutableSetOf()
    validMoves.addAll(generateNormalMove(currentPosition))
    if (currentPosition.rank == startRank) {
      validMoves.addAll(generateDoubleMove(currentPosition))
    }
    validMoves.addAll(generateCaptureMove(currentPosition, Direction.LEFT))
    validMoves.addAll(generateCaptureMove(currentPosition, Direction.RIGHT))

    return validMoves
  }

  private fun generateNormalMove(currentPosition: Position): Set<Move> {
    val nextPosition = currentPosition.nextPosition(forwardDirection)
    return if (boardInspector.isSquareEmpty(nextPosition) &&
      nextPosition.rank == promotionRank
    ) {
      generatePromotionMoves(currentPosition, nextPosition)
    } else if (boardInspector.isSquareEmpty(nextPosition)) {
      setOf(SingleMove(currentPosition, nextPosition))
    } else {
      emptySet()
    }
  }

  private fun generateDoubleMove(
    currentPosition: Position,
  ): Set<DoublePawnMove> {
    val nextPosition = currentPosition.nextPosition(forwardDirection)
    val isNextPositionEmpty = boardInspector.isSquareEmpty(nextPosition)
    val nextNextPosition = nextPosition.nextPosition(forwardDirection)
    val isNextNextPositionEmpty = boardInspector.isSquareEmpty(nextNextPosition)

    return if (isNextPositionEmpty && isNextNextPositionEmpty) {
      setOf(DoublePawnMove(currentPosition, nextNextPosition))
    } else {
      emptySet()
    }
  }

  private fun generateCaptureMove(
    currentPosition: Position,
    captureDirection: Direction,
  ): Set<Move> {
    if (currentPosition.hasNextPosition(captureDirection)) {
      val nextPosition = currentPosition.nextPosition(captureDirection)
        .nextPosition(forwardDirection)
      val isEnemyOnTarget: Boolean =
        boardInspector.getPieceAt(nextPosition)?.color == color.invert()

      return if (isEnemyOnTarget && nextPosition.rank == promotionRank) {
        generatePromotionMoves(currentPosition, nextPosition)
      } else if (isEnemyOnTarget) {
        setOf(SingleMove(currentPosition, nextPosition))
      } else {
        emptySet()
      }
    }
    return emptySet()
  }

  private fun generatePromotionMoves(from: Position, to: Position): Set<Move> =
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
