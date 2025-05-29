package hwr.oop.group8.chess.persistence

import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.Game
import hwr.oop.group8.chess.core.Rank
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import kotlin.io.path.createTempFile
import kotlin.io.path.deleteExisting
import kotlin.io.path.name
import kotlin.io.path.readText
import kotlin.io.path.writeText

class FilePersistenceAdapterTest : AnnotationSpec() {

  @Test
  fun `Checking if Adapter has right id and file name`() {
    val tempFile = createTempFile()
    val sut = FilePersistenceAdapter(tempFile.toFile())
    assertThat(sut.file.name).isEqualTo(tempFile.name)

    tempFile.deleteExisting()
  }

  @Test
  fun `Should read game from file and return correct FEN object`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkb1r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1" +
        "${System.lineSeparator()}",
    )

    val sut = FilePersistenceAdapter(tempFile.toFile())
    val result: FENData = sut.loadGame(1).getFenData()

    assertThat(result.getRank(Rank.EIGHT)).isEqualTo("rnbqkb1r")
    assertThat(result.getRank(Rank.SEVEN)).isEqualTo("pppppppp")
    assertThat(result.getRank(Rank.SIX)).isEqualTo("8")
    assertThat(result.getRank(Rank.FIVE)).isEqualTo("8")
    assertThat(result.getRank(Rank.FOUR)).isEqualTo("8")
    assertThat(result.getRank(Rank.THREE)).isEqualTo("8")
    assertThat(result.getRank(Rank.TWO)).isEqualTo("PPPPPPPP")
    assertThat(result.getRank(Rank.ONE)).isEqualTo("RNBQKB1R")
    assertThat(result.getTurn()).isEqualTo(Color.WHITE)
    assertThat(result.castle).isEqualTo("KQkq")
    assertThat(result.halfmoveClock).isEqualTo(0)
    assertThat(result.fullmoveClock).isEqualTo(1)

    tempFile.deleteExisting()
  }

  @Test
  fun `Should throw exception when game with given id does not exist`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkb1r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1" +
        "${System.lineSeparator()}",
    )

    val sut = FilePersistenceAdapter(tempFile.toFile())

    assertThatThrownBy {
      sut.loadGame(2)
    }.message().contains("Could not load game with id 2")

    tempFile.deleteExisting()
  }

  @Test
  fun `Should select the the game with id 2`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkb1r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1" +
        "${System.lineSeparator()}" +
        "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b " +
        "KQkq - 0 12" +
        "${System.lineSeparator()}" +
        "3,rnbqkb1r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1" +
        "${System.lineSeparator()}",
    )

    val sut = FilePersistenceAdapter(tempFile.toFile())
    val result: FENData = sut.loadGame(2).getFenData()

    assertThat(result.getRank(Rank.EIGHT)).isEqualTo("r3k2r")
    assertThat(result.getRank(Rank.SEVEN)).isEqualTo("p1ppqpb1")
    assertThat(result.getRank(Rank.SIX)).isEqualTo("bn2pnp1")
    assertThat(result.getRank(Rank.FIVE)).isEqualTo("3PN3")
    assertThat(result.getRank(Rank.FOUR)).isEqualTo("1p2P3")
    assertThat(result.getRank(Rank.THREE)).isEqualTo("2N2Q1p")
    assertThat(result.getRank(Rank.TWO)).isEqualTo("PPPBBPPP")
    assertThat(result.getRank(Rank.ONE)).isEqualTo("R3K2R")
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

    val sut = FilePersistenceAdapter(tempFile.toFile())

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

    val sut = FilePersistenceAdapter(tempFile.toFile())

    sut.saveGame(initialGame, false)

    val result = tempFile.readText()
    assertThat(
      result,
    ).isEqualTo("1,rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")

    tempFile.deleteExisting()
  }

  @Test
  fun `Game 1 should be updated correctly`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1" +
        "${System.lineSeparator()}" +
        "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b " +
        "KQkq - 0 12" +
        "${System.lineSeparator()}",
    )

    val sut = FilePersistenceAdapter(tempFile.toFile())
    val fenData = FENData(
      "rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR",
      'b',
      "KQkq",
      "-",
      0,
      1,
    )

    sut.saveGame(Game(1, fenData), true)

    val result = tempFile.readText()
    assertThat(result).isEqualTo(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR b KQkq - 0 1" +
        "${System.lineSeparator()}" +
        "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b " +
        "KQkq - 0 12",
    )

    tempFile.deleteExisting()
  }

  @Test
  fun `Using a id that not exits should throw a exception`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1" +
        "${System.lineSeparator()}" +
        "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b " +
        "KQkq - 0 12" +
        "${System.lineSeparator()}",
    )

    val sut = FilePersistenceAdapter(tempFile.toFile())
    val fenData = FENData(
      "rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR",
      'b',
      "KQkq",
      "-",
      0,
      1,
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
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1" +
        "${System.lineSeparator()}" +
        "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b " +
        "KQkq - 0 12" +
        "${System.lineSeparator()}",
    )
    val initialBoard = Game(1, FENData())

    val sut = FilePersistenceAdapter(tempFile.toFile())

    assertThatThrownBy {
      sut.saveGame(initialBoard, false)
    }.message().contains("Game with id 1 already exists")

    tempFile.deleteExisting()
  }

  @Test
  fun `list games should print all saved game with id and turn`() {
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1" +
        "${System.lineSeparator()}" +
        "3,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b " +
        "KQkq - 0 12" +
        "${System.lineSeparator()}",
    )

    val sut = FilePersistenceAdapter(tempFile.toFile())
    val games = sut.loadAllGames()

    assertThat(games).hasSize(2)
    assertThat(games[0].id).isEqualTo(1)
    assertThat(games[0].getFenData().getTurn()).isEqualTo(Color.WHITE)
    assertThat(games[1].id).isEqualTo(3)
    assertThat(games[1].getFenData().getTurn()).isEqualTo(Color.BLACK)

    tempFile.deleteExisting()
  }

  @Test
  fun `Game with id 2 should be deleted`() {
    // given
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1" +
        "${System.lineSeparator()}" +
        "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b " +
        "KQkq - 0 12" +
        "${System.lineSeparator()}" +
        "3,rnbqkb1r/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1" +
        "${System.lineSeparator()}",
    )
    val sut = FilePersistenceAdapter(tempFile.toFile())

    // when
    sut.deleteGame(2)
    val remainingGames = sut.loadAllGames()

    // then
    assertThat(remainingGames).hasSize(2)
    assertThat(remainingGames.map { it.id }).doesNotContain(2)

    tempFile.deleteExisting()
  }

  @Test
  fun `Deleting non existing game should throw exception`() {
    // given
    val tempFile = createTempFile()
    tempFile.writeText(
      "1,rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1" +
        "${System.lineSeparator()}" +
        "2,r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R b " +
        "KQkq - 0 12" +
        "${System.lineSeparator()}",
    )
    val sut = FilePersistenceAdapter(tempFile.toFile())
    // when
    assertThatThrownBy {
      sut.deleteGame(3)
    }.isInstanceOf(CouldNotDeleteGameException::class.java)
      .hasMessageContaining("Game with id 3 does not exist")
    // then
    val remainingGames = sut.loadAllGames()
    assertThat(remainingGames).hasSize(2)
    assertThat(remainingGames.map { it.id }).containsExactlyInAnyOrder(1, 2)

    tempFile.deleteExisting()
  }
}
