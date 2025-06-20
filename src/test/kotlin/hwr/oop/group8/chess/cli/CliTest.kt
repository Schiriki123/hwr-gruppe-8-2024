package hwr.oop.group8.chess.cli

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class CliTest : AnnotationSpec() {

  @Test
  fun `Writing existing commands`() {
    // given
    val adapterMock = PersistentAdapterMock()
    val cli = Cli(
      adapterMock,
    )

    // when
    val command = cli.commands

    // then
    assertThat(command).isNotEmpty
  }

  @Test
  fun `Input with no matching command`() {
    // given
    val adapterMock = PersistentAdapterMock()
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
}
