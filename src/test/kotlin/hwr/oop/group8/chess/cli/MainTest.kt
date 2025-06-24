package hwr.oop.group8.chess.cli

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat
import java.io.File
import kotlin.math.abs
import kotlin.random.Random

class MainTest : AnnotationSpec() {
  @Test
  fun `Create game, assert that is was created, delete game`() {
    val testGameID = abs(Random(System.currentTimeMillis()).nextInt())
    val output = captureStandardOut {
      main(arrayOf("new-game", "$testGameID"))
    }.trim()
    assertThat(output).contains("New game with id $testGameID created.")
    assertThat(File("games.csv").readLines().last()).isEqualTo(
      "$testGameID,rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1," +
        "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR".hashCode(),
    )

    val deleteOutput = captureStandardOut {
      main(arrayOf("delete-game", "$testGameID"))
    }.trim()
    assertThat(deleteOutput).contains("Game with ID $testGameID deleted.")
  }

  @Test
  fun `Making invalid move should be caught`() {
    // given
    val testGameID = abs(Random(System.currentTimeMillis()).nextInt())
    main(arrayOf("new-game", "$testGameID"))

    // when
    val output = captureStandardOut {
      main(arrayOf("make-move", "$testGameID", "e2", "e5"))
    }.trim()
    // then
    assertThat(
      output,
    ).isEqualTo("ILLEGAL MOVE: Invalid move for piece Pawn from e2 to e5")
  }

  @Test
  fun `Loading non existing game should be caught`() {
    val testGameID = abs(Random(System.currentTimeMillis()).nextInt())
    val output = captureStandardOut {
      main(arrayOf("show-game", "$testGameID"))
    }.trim()

    assertThat(
      output,
    ).isEqualTo("INVALID ID: Could not load game with id $testGameID")
  }
}
