package hwr.oop.group8.chess.cli

import hwr.oop.group8.chess.core.Game
import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.persistence.PersistencePort
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class CliTest : AnnotationSpec() {

  @Test
  fun `Writing existing commands`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
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
  fun `Make a move via cli`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
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
        'b',
      ),
    )
    assertThat(output).contains("Move made from e2 to e4.")
  }

  @Test
  fun `Try to make move with invalid coordinates`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
    )

    // when
    val args = listOf("make", "move", "1", "e2", "invalid")

    // then
    assertThatThrownBy {
      cli.handle(args)
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "No command found for arguments: $args",
    )
  }

  @Test
  fun `Print board via cli`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
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
        "Current turn: WHITE",
    )
  }

  @Test
  fun `List all games on cli`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
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
        "Game ID: 2, Current turn: BLACK",
    )
  }

  @Test
  fun `Try to list games with invalid additional arguments`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
    )

    // when
    val args = listOf("list", "games", "extra")

    // then
    assertThatThrownBy {
      cli.handle(args)
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "No command found for arguments: $args",
    )
  }

  @Test
  fun `Input with no matching command`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
    )

    // when
    val args = listOf("invalid", "command")

    // then
    assertThatThrownBy {
      cli.handle(args)
    }.isInstanceOf(IllegalArgumentException::class.java).message().isEqualTo(
      "No command found for arguments: $args",
    )
  }

  @Test
  fun `Empty execution should print help`() {
    // given
    val adapterMock = PersistentGameAdapterMock()
    val cli = Cli(
      adapterMock,
    )

    // when
    val output = captureStandardOut {
      val args = emptyList<String>()
      cli.handle(args)
    }.trim()

    // then
    @Suppress("ktlint:standard:max-line-length")
    assertThat(output).isEqualTo(
      "Usage: chess <command> [options]${System.lineSeparator()}" +
        "${System.lineSeparator()}" +
        "Available commands:${System.lineSeparator()}" +
        "  new game <id> - Create a new game with the given ID.${System.lineSeparator()}" +
        "  show game <id> - Print the current state of the game with the given ID.${System.lineSeparator()}" +
        "  make move <id> <start> <end> - Make a move in the game with the given ID.${System.lineSeparator()}" +
        "  list games - List all saved games.${System.lineSeparator()}" +
        "${System.lineSeparator()}" +
        "Options:${System.lineSeparator()}" +
        "  -h, --help - Show this help message.",
    )
  }

  private class PersistentGameAdapterMock : PersistencePort {
    private var game: Game? = null

    fun savedGame(): Game? = game

    override fun loadGame(id: Int): Game {
      // Mock implementation
      return game ?: Game(id, FENData())
    }

    override fun saveGame(game: Game, updateExistingGame: Boolean) {
      // Mock implementation
      this.game = game
    }

    override fun loadAllGames(): List<Game> = listOf(
      Game(1, FENData()),
      Game(2, FENData("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR", 'b')),
    )
  }
}
