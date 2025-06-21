package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.core.piece.Bishop
import hwr.oop.group8.chess.core.piece.King
import hwr.oop.group8.chess.core.piece.Knight
import hwr.oop.group8.chess.core.piece.Pawn
import hwr.oop.group8.chess.core.piece.Queen
import hwr.oop.group8.chess.core.piece.Rook
import hwr.oop.group8.chess.persistence.FEN
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class BoardTest : AnnotationSpec() {
  @Test
  fun `Trying to move piece from empty square should throw`() {
    // given
    val board = Board(FEN())
    val move =
      SingleMove(Position(File.A, Rank.FOUR), Position(File.B, Rank.FOUR))
    // then
    assertThatThrownBy {
      board.makeMove(move)
    }.message().contains("There is no piece at a4")
  }

  @Test
  fun `Moving opponents piece should throw`() {
    // given
    val board = Board(FEN())
    val move =
      SingleMove(Position(File.A, Rank.SEVEN), Position(File.A, Rank.SIX))
    assertThatThrownBy {
      board.makeMove(move)
    }.message().isEqualTo("It's not your turn")
    // then
  }

  @Test
  fun `empty board creation, return standard fen notation-string`() {
    val board = Board(FEN("K7/8/8/8/8/8/8/8", 'w', ""))
    for (rank in Rank.entries.reversed()) {
      for (file in File.entries) {
        val position = Position(file, rank)
        val square = board.getSquare(position)
        if (rank == Rank.EIGHT && file == File.A) {
          square.getPiece().shouldBeInstanceOf<King>()
        } else {
          assertThat(square.getPiece()).isNull()
        }
      }
    }
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("K7/8/8/8/8/8/8/8")
  }

  @Test
  fun `create map with size 64, return map`() {
    val board = Board(FEN("K7/8/8/8/8/8/8/8", 'w', ""))
    val map = board.getMap()

    for (rank in Rank.entries) {
      for (file in File.entries) {
        val position = Position(file, rank)
        assertThat(map[position]).isNotNull
      }
    }
    assertThat(map.size).isEqualTo(64)
  }

  @Test
  fun `create board with default setup, return board with start arrangement`() {
    val board = Board(FEN())
    board.analyser.getPieceAt(Position(File.A, Rank.ONE))
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.WHITE,
      )
    board.analyser.getPieceAt(Position(File.B, Rank.ONE))
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.WHITE,
      )
    board.analyser.getPieceAt(Position(File.C, Rank.ONE))
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.WHITE,
      )
    board.analyser.getPieceAt(Position(File.D, Rank.ONE))
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.WHITE,
      )
    board.analyser.getPieceAt(Position(File.E, Rank.ONE))
      .shouldBeInstanceOf<King>().color.shouldBe(
        Color.WHITE,
      )
    board.analyser.getPieceAt(Position(File.F, Rank.ONE))
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.WHITE,
      )
    board.analyser.getPieceAt(Position(File.G, Rank.ONE))
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.WHITE,
      )
    board.analyser.getPieceAt(Position(File.H, Rank.ONE))
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.WHITE,
      )
    for (i in File.entries) {
      board.analyser.getPieceAt(Position(i, Rank.TWO))
        .shouldBeInstanceOf<Pawn>().color.shouldBe(
          Color.WHITE,
        )
    }

    // Black pieces
    board.analyser.getPieceAt(Position(File.A, Rank.EIGHT))
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.BLACK,
      )
    board.analyser.getPieceAt(Position(File.B, Rank.EIGHT))
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.BLACK,
      )
    board.analyser.getPieceAt(Position(File.C, Rank.EIGHT))
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.BLACK,
      )
    board.analyser.getPieceAt(Position(File.D, Rank.EIGHT))
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.BLACK,
      )
    board.analyser.getPieceAt(Position(File.E, Rank.EIGHT))
      .shouldBeInstanceOf<King>().color.shouldBe(
        Color.BLACK,
      )
    board.analyser.getPieceAt(Position(File.F, Rank.EIGHT))
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.BLACK,
      )
    board.analyser.getPieceAt(Position(File.G, Rank.EIGHT))
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.BLACK,
      )
    board.analyser.getPieceAt(Position(File.H, Rank.EIGHT))
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.BLACK,
      )
    for (i in File.entries) {
      board.analyser.getPieceAt(Position(i, Rank.SEVEN))
        .shouldBeInstanceOf<Pawn>().color.shouldBe(
          Color.BLACK,
        )
    }

    assertThat(board.turn).isEqualTo(Color.WHITE)
    assertThat(board.castle()).isEqualTo("KQkq")
    assertThat(board.enPassant).isEqualTo(null)
    assertThat(board.halfmoveClock).isEqualTo(0)
    assertThat(board.fullmoveClock).isEqualTo(1)
  }

  @Test
  fun `custom board initialization`() {
    val board = Board(FEN("k7/2R4B/8/8/1q6/8/8/2Q4N", 'b', "-", "-", 4, 25))
    board.analyser.getPieceAt(Position(File.B, Rank.FOUR))
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.BLACK,
      )

    board.analyser.getPieceAt(Position(File.A, Rank.EIGHT))
      .shouldBeInstanceOf<King>().color.shouldBe(
        Color.BLACK,
      )

    board.analyser.getPieceAt(Position(File.H, Rank.SEVEN))
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.WHITE,
      )

    board.analyser.getPieceAt(Position(File.C, Rank.SEVEN))
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.WHITE,
      )

    board.analyser.getPieceAt(Position(File.C, Rank.ONE))
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.WHITE,
      )

    board.analyser.getPieceAt(Position(File.H, Rank.ONE))
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.WHITE,
      )

    assertThat(board.turn).isEqualTo(Color.BLACK)
    assertThat(board.castle()).isEqualTo("-")
    assertThat(board.halfmoveClock).isEqualTo(4)
    assertThat(board.fullmoveClock).isEqualTo(25)
  }

  @Test
  fun `halfmoveclock should be 0 after capture`() {
    val board =
      Board(FEN("8/8/8/8/8/7r/7R/k7", 'b', "-", halfmoveClock = 12))
    val startPosition = Position(File.H, Rank.THREE)
    val endPosition = Position(File.H, Rank.TWO)
    val testSingleMove = SingleMove(startPosition, endPosition)
    board.makeMove(testSingleMove)
    assertThat(board.halfmoveClock).isEqualTo(0)
  }

  @Test
  fun `reset halfmoveclock after pawn move, expecting halfmoveclock to be 0`() {
    val board =
      Board(FEN("8/8/8/8/8/1p6/8/k7", 'b', "-", halfmoveClock = 12))
    val startPosition = Position(File.B, Rank.THREE)
    val endPosition = Position(File.B, Rank.TWO)
    val testSingleMove = SingleMove(startPosition, endPosition)
    board.makeMove(testSingleMove)
    assertThat(board.halfmoveClock).isEqualTo(0)
  }

  @Test
  fun `increase halfmoveclock expecting halfmoveclock to be 13 `() {
    val board =
      Board(FEN("r7/8/8/8/8/8/8/k7", 'b', "-", halfmoveClock = 12))
    val startPosition = Position(File.A, Rank.EIGHT)
    val endPosition = Position(File.A, Rank.SEVEN)
    val testSingleMove = SingleMove(startPosition, endPosition)
    board.makeMove(testSingleMove)
    assertThat(board.halfmoveClock).isEqualTo(13)
  }

  @Test
  fun `increase fullmove clock expecting fullmove to be 13 `() {
    val board =
      Board(FEN("r7/8/8/8/8/8/8/k7", 'b', "-", fullmoveClock = 12))
    val startPosition = Position(File.A, Rank.EIGHT)
    val endPosition = Position(File.A, Rank.SEVEN)
    val testSingleMove = SingleMove(startPosition, endPosition)
    board.makeMove(testSingleMove)
    assertThat(board.fullmoveClock).isEqualTo(13)
  }

  @Test
  fun `do not increase fullmove clock expecting fullmove to be 12 `() {
    val board =
      Board(FEN("R7/8/8/8/8/8/8/K7", 'w', "-", fullmoveClock = 12))
    val startPosition = Position(File.A, Rank.EIGHT)
    val endPosition = Position(File.A, Rank.SEVEN)
    val testSingleMove = SingleMove(startPosition, endPosition)
    board.makeMove(testSingleMove)
    assertThat(board.fullmoveClock).isEqualTo(12)
  }

  @Test
  fun `Piece movement, rook move e4 to e8 + no exception thrown `() {
    val board = Board(FEN("8/8/8/8/4R3/8/8/K7", 'w', "-"))
    val startPosition = Position(File.E, Rank.FOUR)
    val endPosition = Position(File.E, Rank.EIGHT)
    val testSingleMove = SingleMove(startPosition, endPosition)

    shouldNotThrowAny {
      board.makeMove(testSingleMove)
    }
  }

  @Test
  fun `Invalid board creation`() {
    assertThatThrownBy {
      Board(FEN("rnbqkbnr/pppppppp/8/8/8/7/PPPPPPPP/RNBQKBNR"))
    }.message().isEqualTo("Board must have exactly 64 squares.")

    assertThatThrownBy {
      Board(FEN("K7/8/k7/8/8/8/8/9"))
    }.message().isEqualTo("File iterator should have next element.")
  }

  @Test
  fun `FEN board string creation for default setup`() {
    val board = Board(FEN())
    val fenBoardString = FEN.generateFENBoardString(board)
    assertThat(
      fenBoardString,
    ).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")
  }

  @Test
  fun `FEN board string with custom setup`() {
    val testString = "q4b2/8/8/1Q6/3B4/1PP4K/8/n1n1n1n1"
    val board = Board(FEN(testString))
    val fenBoardString = FEN.generateFENBoardString(board)
    assertThat(fenBoardString).isEqualTo(testString)
  }

  @Test
  fun `Piece moves on ally, exception expected`() {
    val board = Board(FEN("K7/8/8/8/8/P7/8/R7", castle = "-"))
    val singleMove =
      SingleMove(Position(File.A, Rank.ONE), Position(File.A, Rank.THREE))
    assertThatThrownBy { board.makeMove(singleMove) }.message()
      .isEqualTo("Invalid move for piece Rook from a1 to a3")
    assertThat(
      FEN.generateFENBoardString(board),
    ).isEqualTo("K7/8/8/8/8/P7/8/R7")
  }

  @Test
  fun `Capture Piece, rook captures pawn, expecting valid move`() {
    val board = Board(FEN("K7/8/8/8/8/p7/8/R7", castle = "-"))
    val singleMove =
      SingleMove(Position(File.A, Rank.ONE), Position(File.A, Rank.THREE))
    board.makeMove(singleMove)
    assertThat(FEN.generateFENBoardString(board)).isEqualTo("K7/8/8/8/8/R7/8/8")
  }
}
