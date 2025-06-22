package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.testing.test
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class ShowGameTest : AnnotationSpec() {
  @Test
  fun `Print board via cli`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = ShowGame(adapterMock)

    // when
    val args = listOf("1")
    val result = cli.test(args)

    // then
    assertThat(result.stdout.trim()).isEqualTo(
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
