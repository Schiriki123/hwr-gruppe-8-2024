package hwr.oop.group8.chess.cli

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions
import kotlin.random.Random

class MainTest : AnnotationSpec() {

  @Test
  fun `main prints hello world to stdout`() {
    val randomGameId = Random.nextInt(1, 1000)
    val output = captureStandardOut {
      main(arrayOf("new", "game", "$randomGameId"))
    }.trim()
    Assertions.assertThat(output).contains("New game with id $randomGameId created.")
  }

}
