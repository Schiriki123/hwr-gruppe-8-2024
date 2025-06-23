package hwr.oop.group8.chess.cli

import com.github.ajalt.clikt.testing.test
import hwr.oop.group8.chess.core.Game
import hwr.oop.group8.chess.persistence.CouldNotDeleteGameException
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class DeleteGameTest : AnnotationSpec() {
  @Test
  fun `Game should be deleted`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val gameToDelete = Game(1, FEN())
    adapterMock.saveGame(gameToDelete, false)
    val command = DeleteGame(adapterMock)

    // when
    val args = listOf("1")
    val result = command.test(args)

    // then
    val mockGame = adapterMock.savedGame()
    assertThat(mockGame).isNull()
    assertThat(result.stdout).contains("Game with ID 1 deleted.")
  }

  @Test
  fun `Deleting unknown game should throw `() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = DeleteGame(adapterMock)

    // when
    val args = listOf("1")

    // then
    assertThatThrownBy {
      cli.test(args)
    }.isInstanceOf(CouldNotDeleteGameException::class.java)
      .hasMessageContaining("Game with id 1 does not exist")
  }
}
