package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.move.PromotionMove
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class PawnPromotionTest : AnnotationSpec() {
  @Test
  fun `Pawn promotes to queen`() {
    val board = Board(FEN("8/P7/8/8/8/8/8/K7", 'w', ""))
    val move: Move =
      PromotionMove(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.EIGHT),
        PieceType.QUEEN,
      )
    board.makeMove(move)

    assertThat(FEN.generateFENBoardString(board)).isEqualTo("Q7/8/8/8/8/8/8/K7")
  }

  @Test
  fun `Pawn with queen promotion and movement`() {
    val board = Board(FEN("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    var move: Move =
      PromotionMove(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.EIGHT),
        PieceType.QUEEN,
      )

    // Pawn promotes
    board.makeMove(move)

    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("Q7/6kp/8/8/8/8/8/K7")

    // Black Moves
    move =
      SingleMove(Position(File.H, Rank.SEVEN), Position(File.H, Rank.SIX))
    board.makeMove(move)

    // Pawn Moves with Queen movement
    move =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.A, Rank.TWO))
    board.makeMove(move)

    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/6k1/7p/8/8/8/Q7/K7")
  }

  @Test
  fun `Black pawn with knight promotion and movement`() {
    val board = Board(FEN("k7/8/8/8/8/8/7p/K7", 'b', ""))
    var move: Move =
      PromotionMove(
        Position(File.H, Rank.TWO),
        Position(File.H, Rank.ONE),
        PieceType.KNIGHT,
      )

    // Pawn promotes
    board.makeMove(move)

    // Move white king
    board.makeMove(
      SingleMove(
        Position(File.A, Rank.ONE),
        Position(File.A, Rank.TWO),
      ),
    )
    move =
      SingleMove(Position(File.H, Rank.ONE), Position(File.F, Rank.TWO))
    board.makeMove(move)
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("k7/8/8/8/8/8/K4n2/8")
  }

  @Test
  fun `Black pawn with bishop promotion and movement`() {
    val board = Board(FEN("k7/8/8/8/8/8/7p/K7", 'b', ""))
    var move: Move =
      PromotionMove(
        Position(File.H, Rank.TWO),
        Position(File.H, Rank.ONE),
        PieceType.BISHOP,
      )

    // Pawn promotes
    board.makeMove(move)
    // Move white king
    board.makeMove(
      SingleMove(
        Position(File.A, Rank.ONE),
        Position(File.A, Rank.TWO),
      ),
    )
    move = SingleMove(Position(File.H, Rank.ONE), Position(File.F, Rank.THREE))
    board.makeMove(move)
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("k7/8/8/8/8/5b2/K7/8")
  }

  @Test
  fun `Black pawn with rook promotion and movement`() {
    val board = Board(FEN("k7/8/8/8/8/8/7p/K7", 'b', ""))
    var move: Move =
      PromotionMove(
        Position(File.H, Rank.TWO),
        Position(File.H, Rank.ONE),
        PieceType.ROOK,
      )

    // Pawn promotes
    board.makeMove(move)

    // Move white king
    board.makeMove(
      SingleMove(
        Position(File.A, Rank.ONE),
        Position(File.A, Rank.TWO),
      ),
    )
    move =
      SingleMove(Position(File.H, Rank.ONE), Position(File.H, Rank.FOUR))
    board.makeMove(move)
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("k7/8/8/8/7r/8/K7/8")
  }

  @Test
  fun `Capture during promotion`() {
    // given
    val board = Board(FEN("1n6/P7/8/8/8/8/8/K7", castle = ""))
    val move: Move =
      PromotionMove(
        Position(File.A, Rank.SEVEN),
        Position(File.B, Rank.EIGHT),
        PieceType.BISHOP,
      )
    // when
    board.makeMove(move)
    // then
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("1B6/8/8/8/8/8/8/K7")
  }
}
