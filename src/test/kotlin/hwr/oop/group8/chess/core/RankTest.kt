package hwr.oop.group8.chess.core

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

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
}
