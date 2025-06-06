package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.Game
import hwr.oop.group8.chess.persistence.CouldNotDeleteGameException
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class DeleteGameCommandTest : AnnotationSpec() {
  @Test
  fun `Game should be deleted`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val gameToDelete = Game(1, FEN())
    adapterMock.saveGame(gameToDelete, false)
    val cli = Cli(adapterMock)

    // when
    val output = captureStandardOut {
      val args = listOf("delete", "game", "1")
      cli.handle(args)
    }.trim()

    // then
    val mockGame = adapterMock.savedGame()
    assertThat(mockGame).isNull()
    assertThat(output).contains("Game with ID 1 deleted.")
  }

  @Test
  fun `Deleting unknown game should throw `() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(adapterMock)

    // when
    val args = listOf("delete", "game", "1")

    // then
    assertThatThrownBy {
      cli.handle(args)
    }.isInstanceOf(CouldNotDeleteGameException::class.java)
      .hasMessageContaining("Game with id 1 does not exist")
  }
}
