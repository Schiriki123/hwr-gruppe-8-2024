package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class QueenTest : AnnotationSpec() {

  @Test
  fun `char representation`() {
    val boardInspector = Board(FEN("8/8/8/8/8/8/8/K7", 'w', ""))
    val whiteQueen = Queen(Color.WHITE, boardInspector)
    val blackQueen = Queen(Color.BLACK, boardInspector)
    assertThat(whiteQueen.fenRepresentation()).isEqualTo('Q')
    assertThat(blackQueen.fenRepresentation()).isEqualTo('q')
    assertThat(whiteQueen.getType()).isEqualTo(PieceType.QUEEN)
  }

  @Test
  fun `Queen movement on empty board multiple squares down`() {
    val board = Board(FEN("Q6K/8/8/8/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.A, Rank.TWO))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("7K/8/8/8/8/8/Q7/8")
  }

  @Test
  fun `Queen movement on empty board multiple squares up`() {
    val board = Board(FEN("7K/8/8/8/8/8/Q7/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.TWO), Position(File.A, Rank.EIGHT))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("Q6K/8/8/8/8/8/8/8")
  }

  @Test
  fun `Queen movement on empty board multiple squares down right`() {
    val board = Board(FEN("Q6K/8/8/8/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.H, Rank.ONE))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("7K/8/8/8/8/8/8/7Q")
  }

  @Test
  fun `Queen movement on empty board multiple squares up left`() {
    val board = Board(FEN("7K/8/8/8/8/8/8/7Q", 'w', ""))
    val singleMove =
      SingleMove(Position(File.H, Rank.ONE), Position(File.D, Rank.FIVE))
    board.makeMove(singleMove)
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("7K/8/8/3Q4/8/8/8/8")
  }

  @Test
  fun `Queen movement on empty board multiple squares up right`() {
    val board = Board(FEN("7K/8/8/3Q4/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.D, Rank.FIVE), Position(File.G, Rank.EIGHT))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("6QK/8/8/8/8/8/8/8")
  }

  @Test
  fun `Queen movement on empty board multiple squares down left`() {
    val board = Board(FEN("6QK/8/8/8/8/8/8/8", 'w', ""))
    val singleMove =
      SingleMove(Position(File.G, Rank.EIGHT), Position(File.A, Rank.TWO))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("7K/8/8/8/8/8/Q7/8")
  }

  @Test
  fun `invalid Queen movement`() {
    val board = Board(FEN("Q7/8/8/8/8/8/8/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.B, Rank.TWO))
    assertThatThrownBy { board.makeMove(singleMove) }
      .hasMessageContaining("Invalid move for piece Queen from a8 to b2")
  }

  @Test
  fun `Queen movement with straight path blocked by pawn`() {
    val board = Board(FEN("Q7/8/8/8/8/8/P7/1K6", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.A, Rank.ONE))
    assertThatThrownBy { board.makeMove(singleMove) }
      .hasMessageContaining("Invalid move for piece Queen from a8 to a1")
  }

  @Test
  fun `Queen capture with straight moves`() {
    val board = Board(FEN("Q7/8/8/8/8/8/p7/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.A, Rank.TWO))
    board.makeMove(singleMove)

    assertThat(FEN.generateFENBoardString(board)).isEqualTo("8/8/8/8/8/8/Q7/K7")
  }

  @Test
  fun `Diagonal Queen movement with blocked path`() {
    val board = Board(FEN("Q7/8/8/8/4R3/8/8/K7", castle = ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.G, Rank.TWO))
    assertThatThrownBy { board.makeMove(singleMove) }
      .hasMessageContaining("Invalid move for piece Queen from a8 to g2")
  }

  @Test
  fun `Queen capture with diagonal moves`() {
    val board = Board(FEN("Q7/8/8/8/8/8/6p1/K7", 'w', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.G, Rank.TWO))
    board.makeMove(singleMove)

    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/8/8/8/8/8/6Q1/K7")
  }
}
