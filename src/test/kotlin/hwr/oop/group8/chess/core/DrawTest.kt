package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.cli.CliMove
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class DrawTest : AnnotationSpec() {
  @Test
  fun `Creating board with half move clock at 50 should throw`() {
    assertThatThrownBy {
      Board(FEN("r3k2r/8/8/8/8/8/8/R3K2R", 'w', halfmoveClock = 50))
    }.message().isEqualTo("Game is draw due to the 50-move rule.")
  }

  @Test
  fun `Creating board with half move clock at 49 should not throw`() {
    // given
    val board =
      Board(FEN("r3k2r/8/8/8/8/8/8/R3K2R", 'w', halfmoveClock = 49))
    val move =
      SingleMove(Position(File.A, Rank.ONE), Position(File.B, Rank.ONE))
    // when
    board.makeMove(move)
    // then
    assertThat(board.halfmoveClock).isEqualTo(50)
  }

  @Test
  fun `Should be repetition draw when three elements are the same`() {
    // given
    assertThatThrownBy {
      Game(
        1,
        FEN("r3k2r/8/8/8/8/8/8/R3K2R"),
        listOf(888, 112, 888, 112, 888, 122),
      )
    }.message().isEqualTo("Game is draw due to threefold repetition.")
  }

  @Test
  fun `Moving for the third time into the same position should create draw`() {
    // given
    val game = Game(
      1,
      FEN("r3k2r/8/8/8/8/8/8/R3K2R"),
      listOf(-423117847, -423117847),
    )
    val move =
      CliMove(Position(File.A, Rank.ONE), Position(File.B, Rank.ONE), null)
    // when
    game.makeMove(move)
    // then
    assertThat(game.board.isRepetitionDraw()).isTrue
  }

  @Test
  fun `Normal moving should not create draw draw`() {
    // given
    val game = Game(
      1,
      FEN("r3k2r/8/8/8/8/8/8/R3K2R"),
      listOf(-423117847, -423117847),
    )
    val move =
      CliMove(Position(File.A, Rank.ONE), Position(File.A, Rank.TWO), null)
    // when
    game.makeMove(move)
    // then
    assertThat(game.board.isRepetitionDraw()).isFalse
  }
}
