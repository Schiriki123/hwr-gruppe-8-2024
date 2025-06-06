package hwr.oop.group8.chess.cli

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat

class ShowGameCommandTest : AnnotationSpec() {
  @Test
  fun `Print board via cli`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(
      adapterMock,
    )

    // when
    val output = captureStandardOut {
      val args = listOf("show", "game", "1")
      cli.handle(args)
    }.trim()

    // then
    assertThat(output).isEqualTo(
      "Loading game with id 1...${System.lineSeparator()}" +
        "Current board:${System.lineSeparator()}" +
        "rnbqkbnr${System.lineSeparator()}" +
        "pppppppp${System.lineSeparator()}" +
        "........${System.lineSeparator()}" +
        "........${System.lineSeparator()}" +
        "........${System.lineSeparator()}" +
        "........${System.lineSeparator()}" +
        "PPPPPPPP${System.lineSeparator()}" +
        "RNBQKBNR${System.lineSeparator()}" +
        "Current turn: WHITE${System.lineSeparator()}" +
        "White's captures: ${System.lineSeparator()}" +
        "Black's captures:",
    )
  }
}
