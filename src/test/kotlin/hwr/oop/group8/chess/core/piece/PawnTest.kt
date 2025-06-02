package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.PromotionMove
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.SingleMove
import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class PawnTest : AnnotationSpec() {
  @Test
  fun `Char representation as Pawn`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/K7", 'w', ""))
    val whitePawn = Pawn(Color.WHITE, boardInspector)
    val blackPawn = Pawn(Color.BLACK, boardInspector)
    assertThat(whitePawn.getChar()).isEqualTo('P')
    assertThat(blackPawn.getChar()).isEqualTo('p')
    assertThat(whitePawn.getType()).isEqualTo(PieceType.PAWN)
  }

  @Test
  fun `Pawn movement block path`() {
    val board = Board(FENData("7k/p7/R7/8/8/8/8/K7", 'b', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SEVEN), Position(File.A, Rank.SIX))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(board.generateFENBoardString()).isEqualTo("7k/p7/R7/8/8/8/8/K7")
  }

  @Test
  fun `Pawn movement on empty board`() {
    val board = Board(FENData("8/p7/8/8/8/8/8/k7", 'b', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SEVEN), Position(File.A, Rank.SIX))
    board.makeMove(singleMove)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/k7")
  }

  @Test
  fun `Backwards pawn movement`() {
    val board = Board(FENData("8/8/p7/8/8/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SIX), Position(File.A, Rank.SEVEN))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/K7")
  }

  @Test
  fun `Invalid double move, expecting exception`() {
    val board = Board(FENData("8/8/p7/8/8/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SIX), Position(File.A, Rank.FOUR))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/K7")
  }

  @Test
  fun `White pawn movement on empty board`() {
    val board = Board(FENData("8/8/P7/8/8/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SIX), Position(File.A, Rank.SEVEN))
    board.makeMove(singleMove)

    assertThat(board.generateFENBoardString()).isEqualTo("8/P7/8/8/8/8/8/K7")
  }

  @Test
  fun `Valid double move`() {
    val board = Board(FENData("8/8/8/8/8/8/P7/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.TWO), Position(File.A, Rank.FOUR))
    board.makeMove(singleMove)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/P7/8/8/K7")
  }

  @Test
  fun `Valid double move with blocked path`() {
    val board = Board(FENData("8/8/8/8/8/b7/P7/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.TWO), Position(File.A, Rank.FOUR))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/b7/P7/K7")
  }

  @Test
  fun `Invalid diagonal 2 move with white pawn, expecting exception`() {
    val board = Board(FENData("8/8/8/8/8/1R6/P7/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.TWO), Position(File.C, Rank.FOUR))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/1R6/P7/K7")
  }

  @Test
  fun `Diagonal move without capture`() {
    val board = Board(FENData("8/8/8/8/8/8/1P6/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.B, Rank.TWO), Position(File.C, Rank.THREE))

    assertThatThrownBy { board.makeMove(singleMove) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/1P6/K7")
  }

  @Test
  fun `Pawn capture`() {
    val board = Board(FENData("8/8/8/8/8/r7/1P6/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.B, Rank.TWO), Position(File.A, Rank.THREE))

    board.makeMove(singleMove)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/P7/8/K7")
  }

  @Test
  fun `Pawn movement with blocked path`() {
    val board = Board(FENData("8/8/8/8/8/p7/P7/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.TWO), Position(File.A, Rank.THREE))

    assertThatThrownBy { board.makeMove(singleMove) }
  }

  @Test
  fun `Pawn promotes to queen`() {
    val board = Board(FENData("8/P7/8/8/8/8/8/K7", 'w', ""))
    val move: Move =
      PromotionMove(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.EIGHT),
        PieceType.QUEEN,
      )
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("Q7/8/8/8/8/8/8/K7")
  }

  @Test
  fun `Pawn with queen promotion and movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    var move: Move =
      PromotionMove(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.EIGHT),
        PieceType.QUEEN,
      )

    // Pawn promotes
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("Q7/6kp/8/8/8/8/8/K7")

    // Black Moves
    move =
      SingleMove(Position(File.H, Rank.SEVEN), Position(File.H, Rank.SIX))
    board.makeMove(move)

    // Pawn Moves with Queen movement
    move =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.A, Rank.TWO))
    board.makeMove(move)

    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("8/6k1/7p/8/8/8/Q7/K7")
  }

  @Test
  fun `Black pawn with knight promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move: Move =
      PromotionMove(
        Position(File.H, Rank.TWO),
        Position(File.H, Rank.ONE),
        PieceType.KNIGHT,
      )

    // Pawn promotes
    board.makeMove(move)

    board.turn = Color.BLACK
    move =
      SingleMove(Position(File.H, Rank.ONE), Position(File.F, Rank.TWO))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/8/8/5n2/8")
  }

  @Test
  fun `Black pawn with bishop promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move: Move =
      PromotionMove(
        Position(File.H, Rank.TWO),
        Position(File.H, Rank.ONE),
        PieceType.BISHOP,
      )

    // Pawn promotes
    board.makeMove(move)
    board.turn = Color.BLACK
    move = SingleMove(Position(File.H, Rank.ONE), Position(File.F, Rank.THREE))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/8/5b2/8/8")
  }

  @Test
  fun `Black pawn with rook promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move: Move =
      PromotionMove(
        Position(File.H, Rank.TWO),
        Position(File.H, Rank.ONE),
        PieceType.ROOK,
      )

    // Pawn promotes
    board.makeMove(move)
    board.turn = Color.BLACK
    move =
      SingleMove(Position(File.H, Rank.ONE), Position(File.H, Rank.FOUR))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/7r/8/8/8")
  }

  @Test
  fun `Pawn with illegal knight movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move =
      PromotionMove(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.SIX),
        PieceType.KNIGHT,
      )

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Test
  fun `Pawn with illegal bishop movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move =
      PromotionMove(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.SIX),
        PieceType.BISHOP,
      )

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Test
  fun `Pawn with illegal rook movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move =
      PromotionMove(
        Position(File.A, Rank.SEVEN),
        Position(File.C, Rank.FIVE),
        PieceType.ROOK,
      )

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Suppress("ktlint:standard:max-line-length")
  @Test
  fun `Pawn promotion with illegal character should throw, but original game state is restored`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    assertThatThrownBy {
      PromotionMove(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.EIGHT),
        PieceType.fromChar('x'), // Invalid promotion piece
      )
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "Invalid piece character: x",
    )

    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }
}
