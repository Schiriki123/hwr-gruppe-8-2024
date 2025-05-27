package hwr.oop.group8.chess.core

import io.kotest.core.spec.style.AnnotationSpec

class RankTest : AnnotationSpec() {

  @Test
  fun `Rank ONE should be the first rank`() {
    // given
    val rankOne = Rank.ONE
    // when
    val isFirstRank = rankOne == Rank.entries.first()
    // then
    assert(isFirstRank) {
      "Expected Rank.ONE to be the first rank, but it is not."
    }
  }

  @Test
  fun `Rank EIGHT should be the last rank`() {
    // given
    val rankEight = Rank.EIGHT
    // when
    val isLastRank = rankEight == Rank.entries.last()
    // then
    assert(isLastRank) {
      "Expected Rank.EIGHT to be the last rank, but it is not."
    }
  }

  @Test
  fun `One up from rank THREE should be FOUR`() {
    // given
    val rankOne = Rank.THREE
    // when
    val nextRank = rankOne.up()
    // then
    assert(nextRank == Rank.FOUR) { "Expected Rank.TWO, but got $nextRank" }
  }

  @Test
  fun `One down from rank SIX should be FIVE`() {
    // given
    val rankSix = Rank.SIX
    // when
    val previousRank = rankSix.down()
    // then
    assert(previousRank == Rank.FIVE) {
      "Expected Rank.FIVE, but got $previousRank"
    }
  }
}
