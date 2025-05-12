package hwr.oop.group8.chess

import hwr.oop.group8.chess.cli.main
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat

class MainTest : AnnotationSpec() {

  @Test
  @Ignore
  fun `main prints hello world to stdout`() {
    val output = captureStandardOut {
      main(arrayOf("new", "game", "1"))
    }.trim()
    assertThat(output).contains("Game with id 1 created.")
  }

}
