package hwr.oop.group8.chess.piece

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class PieceTypeTest : AnnotationSpec() {
  @Test
  fun `Character q should generate QUEEN`() {
    // given
    val char = 'q'
    // when
    val pieceType = PieceType.fromChar(char)
    // then
    assertThat(pieceType).isEqualTo(PieceType.QUEEN)
  }

  @Test
  fun `Character k should generate KING`() {
    // given
    val char = 'k'
    // when
    val pieceType = PieceType.fromChar(char)
    // then
    assertThat(pieceType).isEqualTo(PieceType.KING)
  }

  @Test
  fun `Character r should generate ROOK`() {
    // given
    val char = 'r'
    // when
    val pieceType = PieceType.fromChar(char)
    // then
    assertThat(pieceType).isEqualTo(PieceType.ROOK)
  }

  @Test
  fun `Character n should generate KNIGHT`() {
    // given
    val char = 'n'
    // when
    val pieceType = PieceType.fromChar(char)
    // then
    assertThat(pieceType).isEqualTo(PieceType.KNIGHT)
  }

  @Test
  fun `Character b should generate BISHOP`() {
    // given
    val char = 'b'
    // when
    val pieceType = PieceType.fromChar(char)
    // then
    assertThat(pieceType).isEqualTo(PieceType.BISHOP)
  }

  @Test
  fun `Character p should generate PAWN`() {
    // given
    val char = 'p'
    // when
    val pieceType = PieceType.fromChar(char)
    // then
    assertThat(pieceType).isEqualTo(PieceType.PAWN)
  }
}
