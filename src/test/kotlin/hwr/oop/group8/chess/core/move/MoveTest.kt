package hwr.oop.group8.chess.core.move

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.piece.King
import hwr.oop.group8.chess.core.piece.PieceType
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class MoveTest : AnnotationSpec() {
  @Test
  fun `Generate Single move, assert that fields are stored correctly`() {
    // given
    val from = Position(File.A, Rank.SEVEN)
    val to = Position(File.A, Rank.SIX)
    // when
    val singleMove = SingleMove(from, to)
    // then
    assertThat(singleMove.from).isEqualTo(from)
    assertThat(singleMove.to).isEqualTo(to)
    assertThat(singleMove.promotesTo()).isNull()
    assertThat(singleMove.isPromotion()).isFalse
  }

  @Test
  fun `Generate new Promotion move`() {
    // given
    val from = Position(File.A, Rank.SEVEN)
    val to = Position(File.A, Rank.EIGHT)
    val pieceType = PieceType.ROOK
    // when
    val promotionMove = PromotionMove(from, to, pieceType)
    // then
    assertThat(promotionMove.from).isEqualTo(from)
    assertThat(promotionMove.to).isEqualTo(to)
    assertThat(promotionMove.promotesTo).isEqualTo(pieceType)
  }

  @Test
  fun `Test king side castle move creation, assert that storage is correct`() {
    val board = Board.factory(FEN("k7/8/8/8/8/8/8/R3KBNR", 'w', "KQ"))
    val from = Position(File.E, Rank.ONE)
    val castleMove = CastleMove(board.analyser.pieceAt(from) as King, true)

    assertThat(castleMove.promotesTo()).isNull()
    assertThat(castleMove.isKingSideCastle).isTrue
    assertThat(castleMove.isPromotion()).isFalse
  }

  @Test
  fun `Test queen side castle move creation, assert that storage is correct`() {
    val board = Board.factory(FEN("k7/8/8/8/8/8/8/R3K2R", 'w', "KQ"))
    val from = Position(File.E, Rank.ONE)
    val castleMove = CastleMove(board.analyser.pieceAt(from) as King, false)

    assertThat(castleMove.promotesTo()).isNull()
    assertThat(castleMove.isKingSideCastle).isFalse
    assertThat(castleMove.isPromotion()).isFalse
  }

  @Test
  fun `Double Move up from a2 to a4 should skip a3`() {
    // given
    val from = Position(File.A, Rank.TWO)
    val to = Position(File.A, Rank.FOUR)
    val skippedPosition = Position(File.A, Rank.THREE)
    // when
    val doubleMove = DoublePawnMove(from, to)
    // then
    assertThat(doubleMove.skippedPosition()).isEqualTo(skippedPosition)
  }
}
