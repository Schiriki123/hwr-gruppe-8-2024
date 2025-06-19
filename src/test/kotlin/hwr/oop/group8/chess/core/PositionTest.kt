package hwr.oop.group8.chess.core

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class PositionTest : AnnotationSpec() {
  @Test
  fun `Position initialization`() {
    val file = File.A
    val rank = Rank.EIGHT
    val testPosition = Position(file, rank)

    assertThat(testPosition.rank).isEqualTo(rank)
    assertThat(testPosition.file).isEqualTo(file)
  }

  @Test
  fun `Next position top`() {
    val position = Position(File.E, Rank.FOUR)
    val direction = Direction.TOP

    val expectedPosition = Position(File.E, Rank.FIVE)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `Next position bottom`() {
    val position = Position(File.E, Rank.FOUR)
    val direction = Direction.BOTTOM

    val expectedPosition = Position(File.E, Rank.THREE)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `Next position left`() {
    val position = Position(File.E, Rank.FOUR)
    val direction = Direction.LEFT

    val expectedPosition = Position(File.D, Rank.FOUR)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `Next position right`() {
    val position = Position(File.E, Rank.FOUR)
    val direction = Direction.RIGHT

    val expectedPosition = Position(File.F, Rank.FOUR)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `Next position top left`() {
    val position = Position(File.E, Rank.FOUR)
    val direction = Direction.TOP_LEFT

    val expectedPosition = Position(File.D, Rank.FIVE)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `Next position top right`() {
    val position = Position(File.E, Rank.FOUR)
    val direction = Direction.TOP_RIGHT

    val expectedPosition = Position(File.F, Rank.FIVE)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `Next position bottom left`() {
    val position = Position(File.E, Rank.FOUR)
    val direction = Direction.BOTTOM_LEFT

    val expectedPosition = Position(File.D, Rank.THREE)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `Next position bottom right`() {
    val position = Position(File.E, Rank.FOUR)
    val direction = Direction.BOTTOM_RIGHT

    val expectedPosition = Position(File.F, Rank.THREE)
    val newPosition = position.nextPosition(direction)

    assertThat(newPosition).isEqualTo(expectedPosition)
  }

  @Test
  fun `Creating position with string length other then 2 should throw`() {
    val invalidPositionString = "Z9Z"
    assertThatThrownBy {
      Position.fromString(invalidPositionString)
    }.hasMessageContaining("Invalid position string: $invalidPositionString")
  }
}
