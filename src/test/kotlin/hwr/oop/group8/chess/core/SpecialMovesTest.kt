package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class SpecialMovesTest : AnnotationSpec() {

  @Test
  fun `Move into check, expecting exception`() {
    val board = Board(FEN("k7/1R6/8/8/8/8/K7/8", turn = 'b', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.B, Rank.EIGHT))
    assertThatThrownBy {
      board.makeMove(singleMove)
    }.message().isEqualTo("Move would put player in check")
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("k7/1R6/8/8/8/8/K7/8")
    assertThat(board.turn()).isEqualTo(Color.BLACK)
  }

  @Test
  fun `King in check, expecting king moves out of check and color change`() {
    val board = Board(FEN("k7/8/R7/8/8/8/K7/8", turn = 'b'))
    val singleMoveToCheck =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.A, Rank.SEVEN))
    assertThatThrownBy { board.makeMove(singleMoveToCheck) }.message()
      .isEqualTo("Move would put player in check")

    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("k7/8/R7/8/8/8/K7/8")
    assertThat(board.turn()).isEqualTo(Color.BLACK)
  }

  @Test
  fun `King moving out of check`() {
    val board = Board(FEN("k7/8/R7/8/8/8/K7/8", turn = 'b'))

    val validSingleMove =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.B, Rank.SEVEN))
    board.makeMove(validSingleMove)
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/1k6/R7/8/8/8/K7/8")
    assertThat(board.turn()).isEqualTo(Color.WHITE)
  }

  @Test
  fun `Random move that sets king in check, expecting check`() {
    val board = Board(FEN("k7/8/r7/8/8/8/R7/8", turn = 'b', ""))
    val singleMove =
      SingleMove(Position(File.A, Rank.SIX), Position(File.C, Rank.SIX))
    assertThatThrownBy { board.makeMove(singleMove) }.message()
      .isEqualTo("Move would put player in check")
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("k7/8/r7/8/8/8/R7/8")
    assertThat(board.turn()).isEqualTo(Color.BLACK)
  }

  @Test
  fun `Position is be checkmate, move should not be allowed`() {
    val board = Board(FEN("k1R5/5R2/8/8/8/8/K7/8", 'b', ""))
    val move =
      SingleMove(Position(File.A, Rank.EIGHT), Position(File.B, Rank.EIGHT))
    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("Game is over, checkmate!")
  }

  @Test
  fun `Turn black after white moves`() {
    val board = Board(FEN("r3k2r/8/8/8/8/8/8/R3K2R"))
    val singleMove =
      SingleMove(Position(File.H, Rank.ONE), Position(File.G, Rank.ONE))
    board.makeMove(singleMove)
    assertThat(board.turn()).isEqualTo(Color.BLACK)
  }
}
