package hwr.oop.group8.chess.cli

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat
import java.io.File
import kotlin.random.Random

class MainTest : AnnotationSpec() {

  @Test
  fun `Create game, assert that is was created, delete game`() {
    val testGameID = Random(System.currentTimeMillis()).nextInt()
    val output = captureStandardOut {
      main(arrayOf("new", "game", "$testGameID"))
    }.trim()
    assertThat(output).contains("New game with id $testGameID created.")
    assertThat(File("games.txt").readLines().last()).isEqualTo(
      "$testGameID,rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
    )

    val deleteOutput = captureStandardOut {
      main(arrayOf("delete", "game", "$testGameID"))
    }.trim()
    assertThat(deleteOutput).contains("Game with ID $testGameID deleted.")
  }
}
