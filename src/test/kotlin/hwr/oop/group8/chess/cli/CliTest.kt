package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.Game
import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.persistence.InitGameInterface
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat

class CliTest : AnnotationSpec() {
  @Test
  fun `Use cli to create new game`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(adapterMock)

    // when
    val output = captureStandardOut {
      val args = listOf("new", "game", "1")
      cli.handle(args)
    }.trim()

    // then
    val game = adapterMock.savedGame()
    requireNotNull(game)
    assertThat(game.id).isEqualTo(1)
    assertThat(game.fenData).isEqualTo(FENData())
    assertThat(output).contains("New game with id 1 created.")
  }

  private class PersistentGameAdapterMock : InitGameInterface {
    private var game: Game? = null

    fun savedGame(): Game? {
      return game
    }

    override fun initGame(id: Int) {
      // Mock implementation
      game = Game(id, FENData())
      println("Mock game with id $id created.")
    }
  }
}
