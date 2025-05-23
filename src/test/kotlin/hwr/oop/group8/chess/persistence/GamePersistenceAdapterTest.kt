package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Game
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import kotlin.io.path.*

class GamePersistenceAdapterTest : AnnotationSpec() {

  @Test
  fun `Test that Adapter has right id and file name`() {
    val tempFile = createTempFile()
    val sut = GamePersistenceAdapter(tempFile.toFile())
    assertThat(sut.file.name).isEqualTo(tempFile.name)
  }

  @Test
  fun `Should read game from file and return correct FEN object`() {
    val tempFile = createTempFile()
    tempFile.writeText("1,rnbqkb1r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1${System.lineSeparator()}")

    val sut = GamePersistenceAdapter(tempFile.toFile())
    val result: FENData = sut.loadGame(1).getFenData()

    assertThat(result.getRank(8)).isEqualTo("rnbqkb1r")
    assertThat(result.getRank(7)).isEqualTo("pppppppp")
    assertThat(result.getRank(6)).isEqualTo("8")
    assertThat(result.getRank(5)).isEqualTo("8")
    assertThat(result.getRank(4)).isEqualTo("8")
    assertThat(result.getRank(3)).isEqualTo("8")
    assertThat(result.getRank(2)).isEqualTo("PPPPPPPP")
    assertThat(result.getRank(1)).isEqualTo("RNBQKB1R")
    assertThat(result.getTurn()).isEqualTo(Color.WHITE)
    assertThat(result.castle).isEqualTo("KQkq")
    assertThat(result.halfmoveClock).isEqualTo(0)
    assertThat(result.fullmoveClock).isEqualTo(1)

    tempFile.deleteExisting()
  }

  @Test
  fun `Should throw exception when game with given id does not exist`() {
    val tempFile = createTempFile()
    tempFile.writeText("1,rnbqkb1r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1${System.lineSeparator()}")

    val sut = GamePersistenceAdapter(tempFile.toFile())

    assertThatThrownBy {
      sut.loadGame(2)
    }.message().contains("Could not load game with id 2")

    tempFile.deleteExisting()
  }

  @Test
  fun `Should select the the game with id 2`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkb1r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1${System.lineSeparator()}" +
          "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b KQkq - 0 12${System.lineSeparator()}" +
          "3,rnbqkb1r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1${System.lineSeparator()}"
    )

    val sut = GamePersistenceAdapter(tempFile.toFile())
    val result: FENData = sut.loadGame(2).getFenData()

    assertThat(result.getRank(8)).isEqualTo("r3k2r")
    assertThat(result.getRank(7)).isEqualTo("p1ppqpb1")
    assertThat(result.getRank(6)).isEqualTo("bn2pnp1")
    assertThat(result.getRank(5)).isEqualTo("3PN3")
    assertThat(result.getRank(4)).isEqualTo("1p2P3")
    assertThat(result.getRank(3)).isEqualTo("2N2Q1p")
    assertThat(result.getRank(2)).isEqualTo("PPPBBPPP")
    assertThat(result.getRank(1)).isEqualTo("R3K2R")
    assertThat(result.getTurn()).isEqualTo(Color.BLACK)
    assertThat(result.castle).isEqualTo("KQkq")
    assertThat(result.halfmoveClock).isEqualTo(0)
    assertThat(result.fullmoveClock).isEqualTo(12)

    tempFile.deleteExisting()
  }

  @Test
  fun `Handle empty file`() {
    val tempFile = createTempFile()
    tempFile.writeText("")

    val sut = GamePersistenceAdapter(tempFile.toFile())

    assertThatThrownBy {
      sut.loadGame(1)
    }.message().contains("Could not load game with id 1")

    tempFile.deleteExisting()
  }

  @Test
  fun `Game create new game`() {
    val tempFile = createTempFile()
    tempFile.writeText("")
    val initialGame = Game(1, FENData())

    val sut = GamePersistenceAdapter(tempFile.toFile())

    sut.saveGame(initialGame, false)

    val result = tempFile.readText()
    assertThat(result).isEqualTo("1,rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")

    tempFile.deleteExisting()
  }

  @Test
  fun `Game 1 should be updated correctly`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1${System.lineSeparator()}" +
          "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b KQkq - 0 12${System.lineSeparator()}"
    )

    val sut = GamePersistenceAdapter(tempFile.toFile())
    val fenData = FENData(
      "rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR",
      'b',
      "KQkq",
      "-",
      0,
      1
    )

    sut.saveGame(Game(1, fenData), true)

    val result = tempFile.readText()
    assertThat(result).isEqualTo(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR b KQkq - 0 1${System.lineSeparator()}" +
          "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b KQkq - 0 12"
    )

    tempFile.deleteExisting()
  }

  @Test
  fun `Using a id that not exits should throw a exception`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1${System.lineSeparator()}" +
          "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b KQkq - 0 12${System.lineSeparator()}"
    )

    val sut = GamePersistenceAdapter(tempFile.toFile())
    val fenData = FENData(
      "rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR",
      'b',
      "KQkq",
      "-",
      0,
      1
    )

    assertThatThrownBy {
      sut.saveGame(Game(3, fenData), true)
    }.message().contains("Game with id 3 does not exist")

    tempFile.deleteExisting()
  }

  @Test
  fun `Creating a game with existing id should fail`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1${System.lineSeparator()}" +
          "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b KQkq - 0 12${System.lineSeparator()}"
    )
    val initialBoard = Game(1, FENData())

    val sut = GamePersistenceAdapter(tempFile.toFile())

    assertThatThrownBy {
      sut.saveGame(initialBoard, false)
    }.message().contains("Game with id 1 already exists")

    tempFile.deleteExisting()
  }

  @Test
  fun `list games should print all saved game with id and turn`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1${System.lineSeparator()}" +
          "3,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b KQkq - 0 12${System.lineSeparator()}"
    )

    val sut = GamePersistenceAdapter(tempFile.toFile())
    val games = sut.loadAllGames()

    assertThat(games).hasSize(2)
    assertThat(games[0].id).isEqualTo(1)
    assertThat(games[0].getFenData().getTurn()).isEqualTo(Color.WHITE)
    assertThat(games[1].id).isEqualTo(3)
    assertThat(games[1].getFenData().getTurn()).isEqualTo(Color.BLACK)

    tempFile.deleteExisting()
  }
}
