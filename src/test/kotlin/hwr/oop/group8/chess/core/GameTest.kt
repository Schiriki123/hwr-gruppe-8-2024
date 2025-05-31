package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.cli.CliMove
import hwr.oop.group8.chess.persistence.FENData
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class GameTest : AnnotationSpec() {

  @Test
  fun `Game receives cliMove with promotion to rook expect to be successful`() {
    // given
    val game = Game(1, FENData("8/P7/7k/8/8/8/8/K7", 'w', ""))
    val cliMove =
      CliMove(Position(File.A, Rank.SEVEN), Position(File.A, Rank.EIGHT), 'R')
    // when
    game.makeMove(cliMove)
    // then
    assertThat(game.board.generateFENBoardString())
      .isEqualTo("R7/8/7k/8/8/8/8/K7")
  }

  @Test
  fun `Try to promote pawn without providing promotion char, should throw`() {
    // given
    val game = Game(1, FENData("8/P7/7k/8/8/8/8/K7", 'w', ""))
    val cliMove =
      CliMove(Position(File.A, Rank.SEVEN), Position(File.A, Rank.EIGHT), null)
    // when
    assertThatThrownBy {
      game.makeMove(cliMove)
    }.isInstanceOf(IllegalStateException::class.java).message()
      .isEqualTo("Promotion move must specify a piece type to promote to")
    // then
    assertThat(game.board.generateFENBoardString())
      .isEqualTo("8/P7/7k/8/8/8/8/K7")
  }

  @Test
  fun `Try to promote pawn with with king char, should throw`() {
    // given
    val game = Game(1, FENData("8/P7/7k/8/8/8/8/K7", 'w', ""))
    val cliMove =
      CliMove(Position(File.A, Rank.SEVEN), Position(File.A, Rank.EIGHT), 'k')
    // when
    assertThatThrownBy {
      game.makeMove(cliMove)
    }.isInstanceOf(IllegalArgumentException::class.java).message()
      .isEqualTo("Invalid promotion piece type: KING")
    // then
    assertThat(game.board.generateFENBoardString())
      .isEqualTo("8/P7/7k/8/8/8/8/K7")
  }
}
