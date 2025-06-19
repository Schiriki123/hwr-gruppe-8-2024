package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat

class NewGameCommandTest : AnnotationSpec() {
  @Test
  fun `Use cli to create new game`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(
      adapterMock,
    )

    // when
    val output = captureStandardOut {
      val args = listOf("new", "game", "1")
      cli.handle(args)
    }.trim()

    // then
    val game = adapterMock.savedGame()
    requireNotNull(game)
    assertThat(game.id).isEqualTo(1)
    assertThat(game.getFen()).isEqualTo(FEN())
    assertThat(output).contains("New game with id 1 created.")
  }
}
