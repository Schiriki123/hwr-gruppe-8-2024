package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.Board
import hwr.oop.group8.chess.Color
import hwr.oop.group8.chess.FENData
import hwr.oop.group8.chess.Move
import hwr.oop.group8.chess.Position
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class RookTest : AnnotationSpec() {
  @Test
  fun `Test char representation`() {
    val whiteRook = Rook(Color.WHITE)
    val blackRook = Rook(Color.BLACK)
    assertThat(whiteRook.getChar()).isEqualTo('R')
    assertThat(blackRook.getChar()).isEqualTo('r')
  }

  @Test
  fun `Test rook movement on empty board`() {
    val board = Board(FENData("R7/8/8/8/8/8/8/8"))
    val move = Move(Position('a', 8), Position('a', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/R7/8")
  }

  @Test
  fun `Test invalid rook movement`() {
    val board = Board(FENData("R7/8/8/8/8/8/8/8"))
    val move = Move(Position('a', 8), Position('b', 2))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move: Is not straight or diagonal")
  }

  @Test
  fun `Test rook movement with path blocked by pawn`() {
    val board = Board(FENData("R7/8/8/8/8/8/P7/8"))
    val move = Move(Position('a', 8), Position('a', 1))
    assertThatThrownBy { board.makeMove(move) }
      .hasMessageContaining("Invalid move for piece Rook from a8 to a1")
  }
}
