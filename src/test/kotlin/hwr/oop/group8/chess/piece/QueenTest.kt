package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Move
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class QueenTest : AnnotationSpec() {
  fun `Test char representation`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/K7"))
    val whiteQueen = Rook(Color.WHITE, boardInspector)
    val blackQueen = Rook(Color.BLACK, boardInspector)
    assertThat(whiteQueen.getChar()).isEqualTo('R')
    assertThat(blackQueen.getChar()).isEqualTo('r')
  }

  @Test
  fun `Test Queen movement on empty board`() {
    val board = Board(FENData("Q6K/8/8/8/8/8/8/8"))
    // Queen moves multiple squares down
    var move = Move(Position('a', 8), Position('a', 2))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("7K/8/8/8/8/8/Q7/8")

    //Queen moves multiple squares up
    move = Move(Position('a', 2), Position('a', 8))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("Q6K/8/8/8/8/8/8/8")
    //Queen moves multiple squares down right
    move = Move(Position('a', 8), Position('h', 1))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("7K/8/8/8/8/8/8/7Q")

    //Queen moves multiple squares up left
    move = Move(Position('h', 1), Position('d', 5))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("7K/8/8/3Q4/8/8/8/8")

    //Queen moves multiple squares up right
    move = Move(Position('d', 5), Position('g', 8))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("6QK/8/8/8/8/8/8/8")

    //Queen moves multiple squares down left
    move = Move(Position('g', 8), Position('a', 2))
    board.turn = Color.WHITE
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("7K/8/8/8/8/8/Q7/8")
  }

  @Test
  fun `Test invalid Queen movement`() {
    val board = Board(FENData("Q7/8/8/8/8/8/8/K7"))
    val move = Move(Position('a', 8), Position('b', 2))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move for piece Queen from a8 to b2")
  }

  @Test
  fun `Test Queen movement with straight path blocked by pawn`() {
    val board = Board(FENData("Q7/8/8/8/8/8/P7/1K6"))
    val move = Move(Position('a', 8), Position('a', 1))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move for piece Queen from a8 to a1")
  }

  @Test
  fun `Test Queen capture with straight moves`() {
    val board = Board(FENData("Q7/8/8/8/8/8/p7/K7"))
    val move = Move(Position('a', 8), Position('a', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/Q7/K7")
  }

  @Test
  fun `Test diagonal Queen movement with blocked path`() {
    val board = Board(FENData("Q7/8/8/8/4R3/8/8/K7"))
    val move = Move(Position('a', 8), Position('g', 2))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move for piece Queen from a8 to g2")
  }

  @Test
  fun `Test Queen capture with diagonal moves`() {
    val board = Board(FENData("Q7/8/8/8/8/8/6p1/K7"))
    val move = Move(Position('a', 8), Position('g', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/6Q1/K7")
  }

}
