package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.BoardInspector
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.move.DoublePawnMove
import hwr.oop.group8.chess.core.move.EnPassantMove
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
    val pos = boardInspector.findPositionOfPiece(this)
    return buildSet {
      addAll(generateNormalMoves(pos))
      if (pos.rank == startRank) addAll(generateDoubleMove(pos))
      addAll(generateCaptureMoves(pos))
      addAll(generateEnPassantMove(pos))
    }
  }

  private fun generateNormalMoves(pos: Position): Set<Move> =
    assembleMoves(pos, normalTargets(pos))

  private fun generateCaptureMoves(pos: Position): Set<Move> = assembleMoves(
    pos,
    diagonalTargets(pos).filter { hasEnemy(it) }.toSet(),
  )

  private fun generateDoubleMove(pos: Position): Set<DoublePawnMove> {
    val next = pos.nextPosition(forwardDirection)
    val nextNext = next.nextPosition(forwardDirection)

    return if (boardInspector.isSquareEmpty(next) &&
      boardInspector.isSquareEmpty(
        nextNext,
      )
    ) {
      setOf(DoublePawnMove(pos, nextNext))
    } else {
      emptySet()
    }
  }

  private fun generateEnPassantMove(pos: Position): Set<EnPassantMove> {
    val enPassantPosition = boardInspector.accessEnPassant()
    return if (enPassantPosition != null &&
      diagonalTargets(pos).contains(enPassantPosition)
    ) {
      setOf(EnPassantMove(pos, enPassantPosition))
    } else {
      emptySet()
    }
  }

  private fun diagonalTargets(pos: Position): Set<Position> =
    listOf(Direction.LEFT, Direction.RIGHT).mapNotNull {
      if (pos.hasNextPosition(it)) {
        pos.nextPosition(it)
          .nextPosition(forwardDirection)
      } else {
        null
      }
    }.toSet()

  private fun normalTargets(pos: Position): Set<Position> {
    val next = pos.nextPosition(forwardDirection)
    return if (boardInspector.isSquareEmpty(next)) setOf(next) else emptySet()
  }

  private fun hasEnemy(pos: Position): Boolean =
    boardInspector.getPieceAt(pos)?.color == color.invert()

  private fun assembleMoves(
    from: Position,
    targets: Set<Position>,
  ): Set<Move> = targets.flatMap { to ->
    if (to.rank == promotionRank) {
      generatePromotionMoves(from, to)
    } else {
      setOf(SingleMove(from, to))
    }
  }.toSet()

  private fun generatePromotionMoves(from: Position, to: Position): Set<Move> =
    setOf(
      PromotionMove(from, to, PieceType.QUEEN),
      PromotionMove(from, to, PieceType.ROOK),
      PromotionMove(from, to, PieceType.BISHOP),
      PromotionMove(from, to, PieceType.KNIGHT),
    )

  override fun getChar(): Char = when (color) {
    Color.WHITE -> 'P'
    Color.BLACK -> 'p'
  }

  override fun getType(): PieceType = PieceType.PAWN
}
