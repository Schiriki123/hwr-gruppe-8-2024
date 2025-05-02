package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.FENData
import hwr.oop.group8.chess.Move
import hwr.oop.group8.chess.Position
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class PawnTest : AnnotationSpec() {
  @Test
  fun `Test char representation`() {
    val whitePawn = Pawn(Color.WHITE)
    val blackPawn = Pawn(Color.BLACK)
    assertThat(whitePawn.getChar()).isEqualTo('P')
    assertThat(blackPawn.getChar()).isEqualTo('p')
  }

  @Test
  fun `Test pawn movement block path`() {
    val board = Board(FENData("8/p7/b7/8/8/8/8/8"))
    val move = Move(Position('a', 7), Position('a', 6))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/p7/b7/8/8/8/8/8")
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
  fun`Test pawn movement to capture`(){
    val board = Board(FENData("8/p7/1P6/8/8/8/8/8"))
    val move = Move(Position('b', 6), Position('a', 7))
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
}
