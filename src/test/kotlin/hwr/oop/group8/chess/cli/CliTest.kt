package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.Game
import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.persistence.InitGameInterface
import hwr.oop.group8.chess.persistence.LoadAllGamesInterface
import hwr.oop.group8.chess.persistence.LoadGameInterface
import hwr.oop.group8.chess.persistence.SaveGameInterface
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.assertAll

class CliTest : AnnotationSpec() {

  @Test
  fun `Test that commands exists`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
      adapterMock,
      adapterMock,
      adapterMock
    )

    // when
    val command = cli.commands

    // then
    assertThat(command).isNotEmpty
  }

  @Test
  fun `Use cli to create new game`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
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
  fun `Try to make move with invalid coordinates`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
      adapterMock,
      adapterMock,
      adapterMock
    )

    // when
    val args = listOf("make", "move", "1", "e2", "invalid")

    // then
    assertThatThrownBy {
      cli.handle(args)
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "No command found for arguments: $args"
    )
  }

  @Test
  fun `print board via cli`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
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
          "Current turn: WHITE"
    )
  }

  @Test
  fun `Test to list all games on cli`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
      adapterMock,
      adapterMock,
      adapterMock
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
          "Game ID: 2, Current turn: BLACK"
    )
  }

  @Test
  fun `Try to list games with invalid additional arguments`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
      adapterMock,
      adapterMock,
      adapterMock
    )

    // when
    val args = listOf("list", "games", "extra")

    // then
    assertThatThrownBy {
      cli.handle(args)
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "No command found for arguments: $args"
    )
  }

  @Test
  fun `Test input with no matching command`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
      adapterMock,
      adapterMock,
      adapterMock
    )

    // when
    val args = listOf("invalid", "command")

    // then
    assertThatThrownBy {
      cli.handle(args)
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "No command found for arguments: $args"
    )
  }

  @Test
  fun `Empty execution should print help`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
      adapterMock,
      adapterMock,
      adapterMock
    )

    // when
    val output = captureStandardOut {
      val args = emptyList<String>()
      cli.handle(args)
    }.trim()

    // then
    assertAll(
      { assertThat(output).contains("Usage: chess <command> [options]") },
      { assertThat(output).contains("Available commands:") },
      { assertThat(output).contains("new game <id>") },
      { assertThat(output).contains("show game <id>") },
      { assertThat(output).contains("make move <id> <start> <end>") },
      { assertThat(output).contains("list games") },
      { assertThat(output).contains("Options:")},
      { assertThat(output).contains(" -h, --help")}
    )
  }

  private class PersistentGameAdapterMock : InitGameInterface,
    LoadGameInterface, SaveGameInterface, LoadAllGamesInterface {
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

    override fun loadAllGames(): List<Game> {
      return listOf(
        Game(1, FENData()),
        Game(2, FENData("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR", 'b')),
      )
    }
  }
}
