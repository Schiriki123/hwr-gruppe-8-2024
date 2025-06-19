package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class MakeMoveCommandTest : AnnotationSpec() {
  @Test
  fun `Try to make move with invalid coordinates`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(adapterMock)

    // when
    val args = listOf("make", "move", "1", "e2", "invalid")

    // then
    assertThatThrownBy {
      cli.handle(args)
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "No command found for arguments: $args",
    )
  }

  @Test
  fun `Make a move via cli`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(adapterMock)

    // when
    val output = captureStandardOut {
      val args = listOf("make", "move", "1", "e2", "e4")
      cli.handle(args)
    }.trim()

    // then
    val game = adapterMock.loadGame(1)
    requireNotNull(game)
    assertThat(game.id).isEqualTo(1)
    assertThat(game.getFen()).isEqualTo(
      FEN(
        "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR",
        'b',
      ),
    )
    assertThat(output).contains("Move made from e2 to e4.")
  }

  @Test
  fun `Try to move with invalid game id`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(adapterMock)
    // when
    val args = listOf("make", "move", "invalid", "e2", "e4")
    // then
    assertThatThrownBy {
      cli.handle(args)
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "No command found for arguments: $args",
    )
  }
}
