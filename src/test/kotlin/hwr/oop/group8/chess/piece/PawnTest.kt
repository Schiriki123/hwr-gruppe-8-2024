package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class PawnTest : AnnotationSpec() {
  @Test
  fun `Test char representation`() {
    val boardInspector = BoardInspector { null }
    val whitePawn = Pawn(Color.WHITE, boardInspector)
    val blackPawn = Pawn(Color.BLACK, boardInspector)
    assertThat(whitePawn.getChar()).isEqualTo('P')
    assertThat(blackPawn.getChar()).isEqualTo('p')
  }

  @Test
  fun `Test pawn movement block path`() {
    val board = Board(FENData("8/p7/R7/8/8/8/8/8"))
    val move = Move(Position('a', 7), Position('a', 6))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/p7/R7/8/8/8/8/8")
  }

  @Test
  fun `Test pawn movement on empty board`() {
    val board = Board(FENData("8/p7/8/8/8/8/8/8"))
    val move = Move(Position('a', 7), Position('a', 6))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/8")
  }

  @Test
  fun `Test backwards pawn movement`() {
    val board = Board(FENData("8/8/p7/8/8/8/8/8"))
    val move = Move(Position('a', 6), Position('a', 7))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/8")
  }

  @Test
  fun `Test invalid double move`() {
    val board = Board(FENData("8/8/p7/8/8/8/8/8"))
    val move = Move(Position('a', 6), Position('a', 4))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/p7/8/8/8/8/8")
  }

  @Test
  fun `Test white pawn movement on empty board`() {
    val board = Board(FENData("8/8/P7/8/8/8/8/8"))
    val move = Move(Position('a', 6), Position('a', 7))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/P7/8/8/8/8/8/8")
  }

  @Test
  fun `Test valid double move`() {
    val board = Board(FENData("8/8/8/8/8/8/P7/8"))
    val move = Move(Position('a', 2), Position('a', 4))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/P7/8/8/8")
  }

  @Test
  fun `Test valid double move with blocked path`() {
    val board = Board(FENData("8/8/8/8/8/b7/P7/8"))
    val move = Move(Position('a', 2), Position('a', 4))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/b7/P7/8")
  }

  @Test
  fun `Test invalid diagonal 2 move with white pawn`() {
    val board = Board(FENData("8/8/8/8/8/1R6/P7/8"))
    val move = Move(Position('a', 2), Position('c', 4))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/1R6/P7/8")
  }

  @Test
  fun `Test diagonal move without capture`() {
    val board = Board(FENData("8/8/8/8/8/8/1P6/8"))
    val move = Move(Position('b', 2), Position('c', 3))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/1P6/8")
  }
}
