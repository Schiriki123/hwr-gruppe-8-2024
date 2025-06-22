package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.testing.test
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class ListGamesTest : AnnotationSpec() {
  @Test
  fun `List all games on cli`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val command = ListGames(adapterMock)

    // when
    val result = command.test()

    // then
    assertThat(result.stdout.trim()).isEqualTo(
      "Loading all games...${System.lineSeparator()}" +
        "List of games:${System.lineSeparator()}" +
        "Game ID: 1, Current turn: WHITE${System.lineSeparator()}" +
        "Game ID: 2, Current turn: BLACK",
    )
  }
}
