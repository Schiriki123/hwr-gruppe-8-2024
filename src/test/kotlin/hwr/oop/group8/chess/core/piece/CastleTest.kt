package hwr.oop.group8.chess.core.piece

import hwr.oop.group8.chess.core.Board
import hwr.oop.group8.chess.core.Color
import hwr.oop.group8.chess.core.File
import hwr.oop.group8.chess.core.Position
import hwr.oop.group8.chess.core.Rank
import hwr.oop.group8.chess.core.move.CastleMove
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class CastleTest : AnnotationSpec() {

  @Test
  fun `Castle string allows white Queen side castled and denies King side`() {
    val board = Board.factory(
      FEN(
        boardString = "r3k2r/8/8/8/8/8/8/R3K2R",
        castle = "Qkq",
        turn = 'w',
      ),
    )
    val allowedCastlingForWhite = board.analyser.isCastlingAllowed(Color.WHITE)
    assertThat(allowedCastlingForWhite.first).isTrue
    assertThat(allowedCastlingForWhite.second).isFalse
  }

  @Test
  fun `Castle string allows white King side castle and denies Queen side`() {
    val board = Board.factory(
      FEN(
        boardString = "r3k2r/8/8/8/8/8/8/R3K2R",
        castle = "Kkq",
        turn = 'w',
      ),
    )
    val allowedCastlingForWhite = board.analyser.isCastlingAllowed(Color.WHITE)
    assertThat(allowedCastlingForWhite.first).isFalse
    assertThat(allowedCastlingForWhite.second).isTrue
  }

  @Test
  fun `Castle string allows black King side castle and denies Queen side`() {
    val board = Board.factory(
      FEN(
        boardString = "r3k2r/8/8/8/8/8/8/R3K2R",
        castle = "Qk",
        turn = 'b',
      ),
    )
    val allowedCastlingForBlack = board.analyser.isCastlingAllowed(Color.BLACK)
    assertThat(allowedCastlingForBlack.first).isFalse
    assertThat(allowedCastlingForBlack.second).isTrue
  }

  @Test
  fun `Move king side tower should remove K from castle`() {
    val board = Board.factory(FEN("r3k2r/8/8/8/8/8/8/R3K2R"))
    val singleMove =
      SingleMove(Position(File.H, Rank.ONE), Position(File.G, Rank.ONE))
    board.makeMove(singleMove)
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("r3k2r/8/8/8/8/8/8/R3K1R1")
    assertThat(board.castle()).isEqualTo("Qkq")
  }

  @Test
  fun `Move queen side tower should remove Q from castle`() {
    val board = Board.factory(FEN("r3k2r/8/8/8/8/8/8/R3K2R"))
    val singleMove =
      SingleMove(Position(File.A, Rank.ONE), Position(File.B, Rank.ONE))
    board.makeMove(singleMove)
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("r3k2r/8/8/8/8/8/8/1R2K2R")
    assertThat(board.castle()).isEqualTo("Kkq")
  }

  // KING
  @Test
  fun `Castle king side for white`() {
    val board = Board.factory(FEN("8/8/8/8/8/8/8/R3K2R"))
    // King side castle
    val singleMove = SingleMove(
      Position(File.E, Rank.ONE),
      Position(File.G, Rank.ONE),
    )
    board.makeMove(singleMove)

    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/8/8/8/8/8/8/R4RK1")
    assertThat(board.castle()).isEqualTo("-")
  }

  @Test
  fun `Castle queen side for white`() {
    val board = Board.factory(FEN("8/8/8/8/8/8/8/R3K2R"))
    val singleMove = SingleMove(
      Position(File.E, Rank.ONE),
      Position(File.C, Rank.ONE),
    )
    board.makeMove(singleMove)

    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/8/8/8/8/8/8/2KR3R")
    assertThat(board.castle()).isEqualTo("-")
  }

  @Test
  fun `Castle king side for black`() {
    val board = Board.factory(FEN("r3k2r/8/8/8/8/8/8/8", 'b'))
    // King side castle
    val singleMove = SingleMove(
      Position(File.E, Rank.EIGHT),
      Position(File.G, Rank.EIGHT),
    )
    board.makeMove(singleMove)

    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("r4rk1/8/8/8/8/8/8/8")
    assertThat(board.castle()).isEqualTo("-")
  }

  @Test
  fun `Castle queen side for black`() {
    val board = Board.factory(FEN("r3k2r/8/8/8/8/8/8/8", 'b'))
    val singleMove = SingleMove(
      Position(File.E, Rank.EIGHT),
      Position(File.C, Rank.EIGHT),
    )
    board.makeMove(singleMove)

    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("2kr3r/8/8/8/8/8/8/8")
    assertThat(board.castle()).isEqualTo("-")
  }

  @Test
  fun `Invalid castle with movement through check, expecting exception`() {
    val board = Board.factory(FEN("8/8/5r2/8/8/8/8/R3K2R"))
    val singleMove =
      SingleMove(Position(File.E, Rank.ONE), Position(File.G, Rank.ONE))
    assertThatThrownBy {
      board.makeMove(singleMove)
    }
  }

  @Test
  fun `Check that castling is not allowed if piece was moved`() {
    val board = Board.factory(FEN("8/k7/8/8/8/8/8/R3K2R", 'w'))
    // Move the rook
    board.makeMove(
      SingleMove(
        Position(File.A, Rank.ONE),
        Position(File.A, Rank.TWO),
      ),
    )
    // Move opponent piece
    board.makeMove(
      SingleMove(
        Position(File.A, Rank.SEVEN),
        Position(File.B, Rank.SEVEN),
      ),
    )
    val startPosition = Position(File.E, Rank.ONE)
    val king = board.analyser.pieceAt(startPosition) as King
    val possibleMoves = king.validMoves()

    assertThat(board.castle()).isEqualTo("K")
    assertThat(
      possibleMoves,
    ).containsExactlyInAnyOrder(
      SingleMove(startPosition, Position(File.D, Rank.ONE)),
      SingleMove(startPosition, Position(File.D, Rank.TWO)),
      SingleMove(startPosition, Position(File.E, Rank.TWO)),
      SingleMove(startPosition, Position(File.F, Rank.ONE)),
      SingleMove(startPosition, Position(File.F, Rank.TWO)),
      CastleMove(king, true),
    )
  }

  @Test
  fun `Try to castle from chess`() {
    val board = Board.factory(FEN("8/1k6/4r3/8/8/8/8/R3K2R"))
    val singleMove =
      SingleMove(Position(File.E, Rank.ONE), Position(File.G, Rank.ONE))
    assertThatThrownBy {
      board.makeMove(singleMove)
    }.message().isEqualTo("Invalid move for piece King from e1 to g1")
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/1k6/4r3/8/8/8/8/R3K2R")
    assertThat(board.castle()).isEqualTo("KQkq")
  }

  @Test
  fun `Try to castle with blocked path king side`() {
    val board = Board.factory(FEN("8/8/8/8/8/8/8/R3KB1R"))
    val singleMove =
      SingleMove(Position(File.E, Rank.ONE), Position(File.G, Rank.ONE))
    assertThatThrownBy {
      board.makeMove(singleMove)
    }.message().isEqualTo("Invalid move for piece King from e1 to g1")
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/8/8/8/8/8/8/R3KB1R")
    assertThat(board.castle()).isEqualTo("KQkq")
  }

  @Test
  fun `Try to castle with blocked path queen side`() {
    val board = Board.factory(FEN("8/8/8/8/8/8/8/RN2K2R"))
    val singleMove =
      SingleMove(Position(File.E, Rank.ONE), Position(File.C, Rank.ONE))
    assertThatThrownBy {
      board.makeMove(singleMove)
    }.message().isEqualTo("Invalid move for piece King from e1 to c1")
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("8/8/8/8/8/8/8/RN2K2R")
    assertThat(board.castle()).isEqualTo("KQkq")
  }

  @Test
  fun `Removing last castling permission should write '-' to file`() {
    // Given
    val board = Board.factory(FEN("4k3/8/8/8/8/8/8/R3K2R", 'w', "KQ"))
    // When
    val move =
      SingleMove(Position(File.E, Rank.ONE), Position(File.E, Rank.TWO))
    board.makeMove(move)
    // Then
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("4k3/8/8/8/8/8/4K3/R6R")
    assertThat(board.castle()).isEqualTo("-")
  }

  @Test
  fun `Black tower is captured without movement, k removed from castle`() {
    // given
    val board = Board.factory(FEN("rnbqkbnr/8/8/8/8/8/1B6/RN1QKBNR"))
    val move =
      SingleMove(Position(File.B, Rank.TWO), Position(File.H, Rank.EIGHT))
    // when
    board.makeMove(move)
    // then
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("rnbqkbnB/8/8/8/8/8/8/RN1QKBNR")
    assertThat(board.castle()).isEqualTo("KQq")
  }

  @Test
  fun `Black tower is captured without movement, q removed from castle`() {
    // given
    val board = Board.factory(FEN("rnbqkbnr/8/8/8/8/8/6B1/RNBQK1NR"))
    val move =
      SingleMove(Position(File.G, Rank.TWO), Position(File.A, Rank.EIGHT))
    // when
    board.makeMove(move)
    // then
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("Bnbqkbnr/8/8/8/8/8/8/RNBQK1NR")
    assertThat(board.castle()).isEqualTo("KQk")
  }
}
