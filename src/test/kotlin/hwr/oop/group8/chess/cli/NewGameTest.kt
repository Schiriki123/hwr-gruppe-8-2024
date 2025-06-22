package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.testing.test
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class NewGameTest : AnnotationSpec() {
  @Test
  fun `Use cli to create new game`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = NewGame(adapterMock)

    // when
    val args = listOf("1")
    val result = cli.test(args)

    // then
    val game = adapterMock.savedGame()
    requireNotNull(game)
    assertThat(game.id).isEqualTo(1)
    assertThat(game.fen()).isEqualTo(FEN())
    assertThat(result.stdout).contains("New game with id 1 created.")
  }
}
