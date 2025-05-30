package hwr.oop.group8.chess.cli

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class ListGamesCommandTest : AnnotationSpec() {
  @Test
  fun `List all games on cli`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(
      adapterMock,
    )

    // when
    val output = captureStandardOut {
      val args = listOf("list", "games")
      cli.handle(args)
    }.trim()

    // then
    assertThat(output).isEqualTo(
      "Loading all games...${System.lineSeparator()}" +
        "List of games:${System.lineSeparator()}" +
        "Game ID: 1, Current turn: WHITE${System.lineSeparator()}" +
        "Game ID: 2, Current turn: BLACK",
    )
  }

  @Test
  fun `Try to list games with invalid additional arguments`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(
      adapterMock,
    )

    // when
    val args = listOf("list", "games", "extra")

    // then
    assertThatThrownBy {
      cli.handle(args)
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "No command found for arguments: $args",
    )
  }
}
