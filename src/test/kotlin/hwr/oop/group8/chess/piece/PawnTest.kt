package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
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
  }

  @Test
  fun `Pawn movement block path`() {
    val board = Board(FENData("7k/p7/R7/8/8/8/8/K7", 'b', ""))
    val move = Move(Position(File.A, Rank.SEVEN), Position(File.A, Rank.SIX))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("7k/p7/R7/8/8/8/8/K7")
  }

  @Test
  fun `Pawn movement on empty board`() {
    val board = Board(FENData("8/p7/8/8/8/8/8/k7", 'b', ""))
    val move = Move(Position(File.A, Rank.SEVEN), Position(File.A, Rank.SIX))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/k7")
  }

  @Test
  fun `Backwards pawn movement`() {
    val board = Board(FENData("8/8/p7/8/8/8/8/K7", 'w', ""))
    val move = Move(Position(File.A, Rank.SIX), Position(File.A, Rank.SEVEN))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/K7")
  }

  @Test
  fun `Invalid double move, expecting exception`() {
    val board = Board(FENData("8/8/p7/8/8/8/8/K7", 'w', ""))
    val move = Move(Position(File.A, Rank.SIX), Position(File.A, Rank.FOUR))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/K7")
  }

  @Test
  fun `White pawn movement on empty board`() {
    val board = Board(FENData("8/8/P7/8/8/8/8/K7", 'w', ""))
    val move = Move(Position(File.A, Rank.SIX), Position(File.A, Rank.SEVEN))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/P7/8/8/8/8/8/K7")
  }

//  @Test
//  fun `white pawn is not promoted`() {
//    val board = Board(FENData("8/8/P7/8/8/8/8/K7", 'w', ""))
//    val move = Move(Position(File.A, Rank.SIX), Position(File.A, Rank.SEVEN))
//    val pawn = board.getPieceAt(Position(File.A, Rank.SIX)) as Pawn
//    board.makeMove(move)
//    assertThat(pawn.promoted).isFalse
//    assertThat(board.generateFENBoardString()).isEqualTo("8/P7/8/8/8/8/8/K7")
//  }

  @Test
  fun `Valid double move`() {
    val board = Board(FENData("8/8/8/8/8/8/P7/K7", 'w', ""))
    val move = Move(Position(File.A, Rank.TWO), Position(File.A, Rank.FOUR))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/P7/8/8/K7")
  }

  @Test
  fun `Valid double move with blocked path`() {
    val board = Board(FENData("8/8/8/8/8/b7/P7/K7", 'w', ""))
    val move = Move(Position(File.A, Rank.TWO), Position(File.A, Rank.FOUR))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/b7/P7/K7")
  }

  @Test
  fun `Invalid diagonal 2 move with white pawn, expecting exception`() {
    val board = Board(FENData("8/8/8/8/8/1R6/P7/K7", 'w', ""))
    val move = Move(Position(File.A, Rank.TWO), Position(File.C, Rank.FOUR))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/1R6/P7/K7")
  }

  @Test
  fun `Diagonal move without capture`() {
    val board = Board(FENData("8/8/8/8/8/8/1P6/K7", 'w', ""))
    val move = Move(Position(File.B, Rank.TWO), Position(File.C, Rank.THREE))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/1P6/K7")
  }

  @Test
  fun `Pawn capture`() {
    val board = Board(FENData("8/8/8/8/8/r7/1P6/K7", 'w', ""))
    val move = Move(Position(File.B, Rank.TWO), Position(File.A, Rank.THREE))

    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/P7/8/K7")
  }

  @Test
  fun `Pawn movement with blocked path`() {
    val board = Board(FENData("8/8/8/8/8/p7/P7/K7", 'w', ""))
    val move = Move(Position(File.A, Rank.TWO), Position(File.A, Rank.THREE))

    assertThatThrownBy { board.makeMove(move) }
  }

  @Test
  fun `Pawn promotes to queen`() {
    val board = Board(FENData("8/P7/8/8/8/8/8/K7", 'w', ""))
    val move =
      Move(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.EIGHT),
        promotionChar = 'q',
      )
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("Q7/8/8/8/8/8/8/K7")
  }

  @Test
  fun `Pawn with queen promotion and movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    var move =
      Move(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.EIGHT),
        promotionChar = 'q',
      )

    // Pawn promotes
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("Q7/6kp/8/8/8/8/8/K7")

    // Black Moves
    move = Move(Position(File.H, Rank.SEVEN), Position(File.H, Rank.SIX))
    board.makeMove(move)

    // Pawn Moves with Queen movement
    move = Move(Position(File.A, Rank.EIGHT), Position(File.A, Rank.TWO))
    board.makeMove(move)

    assertThat(
      board.generateFENBoardString(),
    ).isEqualTo("8/6k1/7p/8/8/8/Q7/K7")
  }

  @Test
  fun `Black pawn with knight promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move =
      Move(
        Position(File.H, Rank.TWO),
        Position(File.H, Rank.ONE),
        promotionChar = 'n',
      )

    // Pawn promotes
    board.makeMove(move)

    board.turn = Color.BLACK
    move = Move(Position(File.H, Rank.ONE), Position(File.F, Rank.TWO))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/8/8/5n2/8")
  }

  @Test
  fun `Black pawn with bishop promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move =
      Move(
        Position(File.H, Rank.TWO),
        Position(File.H, Rank.ONE),
        promotionChar = 'b',
      )
    val pawn: Pawn = board.getPieceAt(Position(File.H, Rank.TWO)) as Pawn

    // Pawn promotes
    board.makeMove(move)
    pawn.promotion('b')
    board.turn = Color.BLACK
    move = Move(Position(File.H, Rank.ONE), Position(File.F, Rank.THREE))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/8/5b2/8/8")
  }

  @Test
  fun `Black pawn with rook promotion and movement`() {
    val board = Board(FENData("k7/8/8/8/8/8/7p/8", 'b', ""))
    var move =
      Move(
        Position(File.H, Rank.TWO),
        Position(File.H, Rank.ONE),
        promotionChar = 'r',
      )
    val pawn: Pawn = board.getPieceAt(Position(File.H, Rank.TWO)) as Pawn

    // Pawn promotes
    board.makeMove(move)
    pawn.promotion('r')
    board.turn = Color.BLACK
    move = Move(Position(File.H, Rank.ONE), Position(File.H, Rank.FOUR))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/8/8/7r/8/8/8")
  }

  @Test
  fun `Pawn with illegal knight movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move =
      Move(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.SIX),
        promotionChar = 'n',
      )

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Test
  fun `Pawn with illegal bishop movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move =
      Move(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.SIX),
        promotionChar = 'b',
      )

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Test
  fun `Pawn with illegal rook movement`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move =
      Move(
        Position(File.A, Rank.SEVEN),
        Position(File.C, Rank.FIVE),
        promotionChar = 'r',
      )

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }

  @Suppress("ktlint:standard:max-line-length")
  @Test
  fun `Pawn promotion with illegal character should throw, but original game state is restored`() {
    val board = Board(FENData("8/P5kp/8/8/8/8/8/K7", 'w', ""))
    val move =
      Move(
        Position(File.A, Rank.SEVEN),
        Position(File.A, Rank.EIGHT),
        promotionChar = 'x',
      )

    assertThatThrownBy { board.makeMove(move) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessageContaining("Invalid promotion piece: x")

    assertThat(board.generateFENBoardString()).isEqualTo("8/P5kp/8/8/8/8/8/K7")
  }
}
