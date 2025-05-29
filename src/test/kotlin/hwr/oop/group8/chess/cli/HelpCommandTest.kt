package hwr.oop.group8.chess.cli

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat

class HelpCommandTest : AnnotationSpec() {
  val helpText = """
    Usage: chess <command> [options]
    
    Available commands:
      new game <id> - Create a new game with the given ID.
      show game <id> - Print the current state of the game with the given ID.
      make move <id> <start> <end> - Make a move in the game with the given ID.
      list games - List all saved games.
      delete game <id> - Delete the game with the given ID.
      help - Show this help message.
  """.trimIndent()

  @Test
  fun `Empty execution should print help`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(
      adapterMock,
    )

    // when
    val output = captureStandardOut {
      val args = emptyList<String>()
      cli.handle(args)
    }.trim()

    // then
    assertThat(output).isEqualTo(helpText)
  }

  @Test
  fun `Command containing help should print help message`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(
      adapterMock,
    )
    // when
    val output = captureStandardOut {
      val args = listOf("help")
      cli.handle(args)
    }.trim()
    // then
    assertThat(output).isEqualTo(helpText)
  }
}
