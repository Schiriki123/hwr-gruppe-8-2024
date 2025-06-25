package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Direction
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.move.DoublePawnMove
import hwr.oop.group8.chess.core.move.PromotionMove
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class PawnTest : AnnotationSpec() {
  @Test
  fun `Right representation of Pawn attributes`() {
    val boardInspector = Board(FEN("8/8/8/8/8/8/8/K7", 'w', ""))
    val whitePawn = Pawn(Color.WHITE, boardInspector.analyser)
    val blackPawn = Pawn(Color.BLACK, boardInspector.analyser)
    assertThat(whitePawn.fenRepresentation()).isEqualTo('P')
    assertThat(blackPawn.fenRepresentation()).isEqualTo('p')
    assertThat(whitePawn.pieceType()).isEqualTo(PieceType.PAWN)
    assertThat(whitePawn.startRank).isEqualTo(Rank.TWO)
    assertThat(whitePawn.forwardDirection).isEqualTo(Direction.TOP)
    assertThat(whitePawn.promotionRank).isEqualTo(Rank.EIGHT)
  }

  @Test
  fun `Pawn movement block path`() {
    val board = Board(FEN("7k/p7/R7/8/8/8/8/K7", 'b', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SEVEN), Position(File.A, Rank.SIX))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("7k/p7/R7/8/8/8/8/K7")
  }

  @Test
  fun `Pawn movement on empty board`() {
    val board = Board(FEN("8/p7/8/8/8/8/8/k7", 'b', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SEVEN), Position(File.A, Rank.SIX))
    board.makeMove(singleMove)

    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/p7/8/8/8/8/k7")
  }

  @Test
  fun `Backwards pawn movement`() {
    val board = Board(FEN("8/8/p7/8/8/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SIX), Position(File.A, Rank.SEVEN))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/p7/8/8/8/8/K7")
  }

  @Test
  fun `Invalid double move, expecting exception`() {
    val board = Board(FEN("8/8/p7/8/8/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SIX), Position(File.A, Rank.FOUR))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/p7/8/8/8/8/K7")
  }

  @Test
  fun `White pawn movement on empty board`() {
    val board = Board(FEN("8/8/P7/8/8/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SIX), Position(File.A, Rank.SEVEN))
    board.makeMove(singleMove)

    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/P7/8/8/8/8/8/K7")
  }

  @Test
  fun `Valid double move`() {
    val board = Board(FEN("8/8/8/8/8/8/P7/K7", 'w', ""))
    val singleMove =
      DoublePawnMove(Position(File.A, Rank.TWO), Position(File.A, Rank.FOUR))
    board.makeMove(singleMove)

    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/8/8/P7/8/8/K7")
  }

  @Test
  fun `Valid double move with blocked path`() {
    val board = Board(FEN("8/8/8/8/8/b7/P7/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.TWO), Position(File.A, Rank.FOUR))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/8/8/8/8/b7/P7/K7")
  }

  @Test
  fun `Invalid diagonal 2 move with white pawn, expecting exception`() {
    val board = Board(FEN("8/8/8/8/8/1R6/P7/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.TWO), Position(File.C, Rank.FOUR))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/8/8/8/8/1R6/P7/K7")
  }

  @Test
  fun `Diagonal move without capture`() {
    val board = Board(FEN("8/8/8/8/8/8/1P6/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.B, Rank.TWO), Position(File.C, Rank.THREE))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/8/8/8/8/8/1P6/K7")
  }

  @Test
  fun `Pawn capture`() {
    val board = Board(FEN("8/8/8/8/8/r7/1P6/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.B, Rank.TWO), Position(File.A, Rank.THREE))

    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/8/8/8/P7/8/K7")
  }

  @Test
  fun `Pawn movement with blocked path`() {
    val board = Board(FEN("8/8/8/8/8/p7/P7/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.TWO), Position(File.A, Rank.THREE))

    assertThatThrownBy { board.makeMove(singleMove) }
  }

  @Suppress("ktlint:standard:max-line-length")
  @Test
  fun `Pawn promotion with illegal character should throw, but original game state is restored`() {
    val board = Board(FEN("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    assertThatThrownBy {
      PromotionMove(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.EIGHT),
        PieceType.fromChar('x'), // Invalid promotion piece
      )
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "Invalid piece character: x",
    )

    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }
}
