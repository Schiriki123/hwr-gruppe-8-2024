package hwr.oop.group8.chess.core

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import kotlin.math.abs

class RankTest : AnnotationSpec() {

  @Test
  fun `Rank ONE should be the first rank`() {
    // given
    val rankOne = Rank.ONE
    // when
    val firstRank = Rank.entries.first()
    // then
    assertThat(rankOne).isEqualTo(firstRank)
  }

  @Test
  fun `Rank EIGHT should be the last rank`() {
    // given
    val rankEight = Rank.EIGHT
    // when
    val lastRank = Rank.entries.last()
    // then
    assertThat(rankEight).isEqualTo(lastRank)
  }

  @Test
  fun `One up from rank THREE should be FOUR`() {
    // given
    val rankOne = Rank.THREE
    // when
    val nextRank = rankOne.up()
    // then
    assertThat(nextRank).isEqualTo(Rank.FOUR)
  }

  @Test
  fun `One down from rank SIX should be FIVE`() {
    // given
    val rankSix = Rank.SIX
    // when
    val previousRank = rankSix.down()
    // then
    assertThat(previousRank).isEqualTo(Rank.FIVE)
  }

  @Test
  fun `fromInt 1 should return Rank ONE`() {
    // given
    val value = 1
    // when
    val rank = Rank.fromInt(value)
    // then
    assertThat(rank).isEqualTo(Rank.ONE)
  }

  @Test
  fun `fromInt 3 should return Rank THREE`() {
    // given
    val value = 3
    // when
    val rank = Rank.fromInt(value)
    // then
    assertThat(rank).isEqualTo(Rank.THREE)
  }

  @Test
  fun `fromInt 5 should return Rank FIVE`() {
    // given
    val value = 5
    // when
    val rank = Rank.fromInt(value)
    // then
    assertThat(rank).isEqualTo(Rank.FIVE)
  }

  @Test
  fun `fromInt 6 should return Rank SIX`() {
    // given
    val value = 6
    // when
    val rank = Rank.fromInt(value)
    // then
    assertThat(rank).isEqualTo(Rank.SIX)
  }

  @Test
  fun `fromInt 7 should return Rank SEVEN`() {
    // given
    val value = 7
    // when
    val rank = Rank.fromInt(value)
    // then
    assertThat(rank).isEqualTo(Rank.SEVEN)
  }

  @Test
  fun `fromInt 8 should return Rank EIGHT`() {
    // given
    val value = 8
    // when
    val rank = Rank.fromInt(value)
    // then
    assertThat(rank).isEqualTo(Rank.EIGHT)
  }

  @Test
  fun `fromInt should throw exception for invalid value`() {
    // given
    val invalidValue = 9
    // when/then
    assertThatThrownBy { Rank.fromInt(invalidValue) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessageContaining("Invalid rank value: $invalidValue")
  }

  @Test
  fun `Distance between TWO and FOUR should be 2`() {
    // given
    val rankTwo = Rank.TWO
    val rankFour = Rank.FOUR
    // when
    val distance = abs(rankTwo.distanceTo(rankFour))
    // then
    assertThat(distance).isEqualTo(2)
  }

  @Test
  fun `Distance between SEVEN and SEVEN should be 0`() {
    // given
    val rankSeven = Rank.SEVEN
    val anotherRankSeven = Rank.SEVEN
    // when
    val distance = abs(rankSeven.distanceTo(anotherRankSeven))
    // then
    assertThat(distance).isEqualTo(0)
  }

  @Test
  fun `Distance between SEVEN and THREE should be 4`() {
    // given
    val rankSeven = Rank.SEVEN
    val rankThree = Rank.THREE
    // when
    val distance = abs(rankSeven.distanceTo(rankThree))
    // then
    assertThat(distance).isEqualTo(4)
  }
}
