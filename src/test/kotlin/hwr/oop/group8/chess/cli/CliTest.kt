package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.Game
import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.persistence.InitGameInterface
import hwr.oop.group8.chess.persistence.LoadGameInterface
import hwr.oop.group8.chess.persistence.SaveGameInterface
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat

class CliTest : AnnotationSpec() {
  @Test
  fun `Use cli to create new game`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
      adapterMock,
      adapterMock
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
    assertThat(game.getFenData()).isEqualTo(FENData())
    assertThat(output).contains("New game with id 1 created.")
  }

  @Test
  fun `Test to make a move via cli`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
      adapterMock,
      adapterMock
    )

    // when
    val output = captureStandardOut {
      val args = listOf("make", "move", "1", "e2", "e4")
      cli.handle(args)
    }.trim()

    // then
    val game = adapterMock.loadGame(1)
    requireNotNull(game)
    assertThat(game.id).isEqualTo(1)
    assertThat(game.getFenData()).isEqualTo(
      FENData(
        "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR",
        'b'
      )
    )
    assertThat(output).contains("Move made from e2 to e4.")
  }

  @Test
  fun `print board via cli`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
      adapterMock,
      adapterMock
    )

    // when
    val output = captureStandardOut {
      val args = listOf("show", "game", "1")
      cli.handle(args)
    }.trim()

    // then
    assertThat(output).isEqualTo(
      "Loading game with id 1...\n" +
          "Current board:\n" +
          "rnbqkbnr\n" +
          "pppppppp\n" +
          "........\n" +
          "........\n" +
          "........\n" +
          "........\n" +
          "PPPPPPPP\n" +
          "RNBQKBNR\n" +
          "Current turn: WHITE"
    )
  }

  private class PersistentGameAdapterMock : InitGameInterface,
    LoadGameInterface, SaveGameInterface {
    private var game: Game? = null

    fun savedGame(): Game? {
      return game
    }

    override fun initGame(id: Int) {
      // Mock implementation
      game = Game(id, FENData())
      println("Mock game with id $id created.")
    }

    override fun loadGame(id: Int): Game {
      // Mock implementation
      return game ?: Game(id, FENData())
    }

    override fun saveGame(game: Game) {
      // Mock implementation
      this.game = game
    }
  }
}
