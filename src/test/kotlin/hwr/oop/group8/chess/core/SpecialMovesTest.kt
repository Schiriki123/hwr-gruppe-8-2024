package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class SpecialMovesTest : AnnotationSpec() {

  @Test
  fun `Move into check, expecting exception`() {
    val board = Board(FENData("k7/1R6/8/8/8/8/K7/8", turn = 'b', ""))
    val move = Move(Position('a', 8), Position('b', 8))
    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("Move would put player in check")
    assertThat(board.generateFENBoardString()).isEqualTo("k7/1R6/8/8/8/8/K7/8")
    assertThat(board.turn).isEqualTo(Color.BLACK)
  }

  @Test
  fun `King in check, expecting king moves out of check and color change`() {
    val board = Board(FENData("k7/8/R7/8/8/8/K7/8", turn = 'b'))
    val moveToCheck = Move(Position('a', 8), Position('a', 7))
    assertThatThrownBy { board.makeMove(moveToCheck) }.message()
      .isEqualTo("Move would put player in check")

    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/R7/8/8/8/K7/8")
    assertThat(board.turn).isEqualTo(Color.BLACK)
  }

  @Test
  fun `King moving out of check`() {
    val board = Board(FENData("k7/8/R7/8/8/8/K7/8", turn = 'b'))

    val validMove = Move(Position('a', 8), Position('b', 7))
    board.makeMove(validMove)
    assertThat(board.generateFENBoardString()).isEqualTo("8/1k6/R7/8/8/8/K7/8")
    assertThat(board.turn).isEqualTo(Color.WHITE)
  }

  @Test
  fun `Random move that sets king in check, expecting check`() {
    val board = Board(FENData("k7/8/r7/8/8/8/R7/8", turn = 'b'))
    val move = Move(Position('a', 6), Position('c', 6))
    assertThatThrownBy { board.makeMove(move) }.message()
      .isEqualTo("Move would put player in check")
    assertThat(board.generateFENBoardString()).isEqualTo("k7/8/r7/8/8/8/R7/8")
    assertThat(board.turn).isEqualTo(Color.BLACK)
  }

  @Test
  fun `Checkmate message Game is over, Checkmate!`() {
    assertThatThrownBy {
      Board(FENData("8/8/8/8/8/8/5r2/1K5r"))
    }.message().isEqualTo("Game is over, checkmate!")
  }

  @Test
  fun `Turn black after white moves`() {
    val board = Board(FENData("r3k2r/8/8/8/8/8/8/R3K2R"))
    val move = Move(Position('h', 1), Position('g', 1))
    board.makeMove(move)
    assertThat(board.turn).isEqualTo(Color.BLACK)
  }
}
