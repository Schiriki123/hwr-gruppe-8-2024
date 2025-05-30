package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.piece.Bishop
import hwr.oop.group8.chess.piece.King
import hwr.oop.group8.chess.piece.Knight
import hwr.oop.group8.chess.piece.Pawn
import hwr.oop.group8.chess.piece.Queen
import hwr.oop.group8.chess.piece.Rook
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class BoardTest : AnnotationSpec() {
  @Test
  fun `empty board creation, return standard fen notation-string`() {
    val board = Board(FENData("K7/8/8/8/8/8/8/8", 'w', ""))
    val capturedPieces = CapturedPieces(board.getMap())
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
    assertThat(board.generateFENBoardString()).isEqualTo("K7/8/8/8/8/8/8/8")
    assertThat(
      capturedPieces.getCapturedPieces(),
    ).isEqualTo(
      "White's captures: rnbqkbnrpppppppp${System.lineSeparator()}Black's captures: RNBQBNRPPPPPPPP",
    )
  }

  @Test
  fun `create map with size 64, return map`() {
    val board = Board(FENData("K7/8/8/8/8/8/8/8", 'w', ""))
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
    val board = Board(FENData())
    val capturedPieces = CapturedPieces(board.getMap())
    board.getSquare(Position(File.A, Rank.ONE)).getPiece()
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.WHITE,
      )
    board.getSquare(Position(File.B, Rank.ONE)).getPiece()
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.WHITE,
      )
    board.getSquare(Position(File.C, Rank.ONE)).getPiece()
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.WHITE,
      )
    board.getSquare(Position(File.D, Rank.ONE)).getPiece()
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.WHITE,
      )
    board.getSquare(Position(File.E, Rank.ONE)).getPiece()
      .shouldBeInstanceOf<King>().color.shouldBe(
        Color.WHITE,
      )
    board.getSquare(Position(File.F, Rank.ONE)).getPiece()
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.WHITE,
      )
    board.getSquare(Position(File.G, Rank.ONE)).getPiece()
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.WHITE,
      )
    board.getSquare(Position(File.H, Rank.ONE)).getPiece()
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.WHITE,
      )
    for (i in File.entries) {
      board.getSquare(Position(i, Rank.TWO)).getPiece()
        .shouldBeInstanceOf<Pawn>().color.shouldBe(
          Color.WHITE,
        )
    }

    // Black pieces
    board.getSquare(Position(File.A, Rank.EIGHT)).getPiece()
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.BLACK,
      )
    board.getSquare(Position(File.B, Rank.EIGHT)).getPiece()
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.BLACK,
      )
    board.getSquare(Position(File.C, Rank.EIGHT)).getPiece()
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.BLACK,
      )
    board.getSquare(Position(File.D, Rank.EIGHT)).getPiece()
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.BLACK,
      )
    board.getSquare(Position(File.E, Rank.EIGHT)).getPiece()
      .shouldBeInstanceOf<King>().color.shouldBe(
        Color.BLACK,
      )
    board.getSquare(Position(File.F, Rank.EIGHT)).getPiece()
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.BLACK,
      )
    board.getSquare(Position(File.G, Rank.EIGHT)).getPiece()
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.BLACK,
      )
    board.getSquare(Position(File.H, Rank.EIGHT)).getPiece()
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.BLACK,
      )
    for (i in File.entries) {
      board.getSquare(Position(i, Rank.SEVEN)).getPiece()
        .shouldBeInstanceOf<Pawn>().color.shouldBe(
          Color.BLACK,
        )
    }

    assertThat(board.turn).isEqualTo(Color.WHITE)
    assertThat(board.castle).isEqualTo("KQkq")
    assertThat(board.enPassant).isEqualTo("-")
    assertThat(board.halfmoveClock).isEqualTo(0)
    assertThat(board.fullmoveClock).isEqualTo(1)

    assertThat(
      capturedPieces.getCapturedPieces(),
    ).isEqualTo(
      "White's captures: ${System.lineSeparator()}Black's captures: ",
    )
  }

  @Test
  fun `custom board initialization`() {
    val board = Board(FENData("k7/2R4B/8/8/1q6/8/8/2Q4N", 'b', "", "-", 4, 25))
    board.getSquare(Position(File.B, Rank.FOUR)).getPiece()
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.BLACK,
      )

    board.getSquare(Position(File.A, Rank.EIGHT)).getPiece()
      .shouldBeInstanceOf<King>().color.shouldBe(
        Color.BLACK,
      )

    board.getSquare(Position(File.H, Rank.SEVEN)).getPiece()
      .shouldBeInstanceOf<Bishop>().color.shouldBe(
        Color.WHITE,
      )

    board.getSquare(Position(File.C, Rank.SEVEN)).getPiece()
      .shouldBeInstanceOf<Rook>().color.shouldBe(
        Color.WHITE,
      )

    board.getSquare(Position(File.C, Rank.ONE)).getPiece()
      .shouldBeInstanceOf<Queen>().color.shouldBe(
        Color.WHITE,
      )

    board.getSquare(Position(File.H, Rank.ONE)).getPiece()
      .shouldBeInstanceOf<Knight>().color.shouldBe(
        Color.WHITE,
      )

    assertThat(board.turn).isEqualTo(Color.BLACK)
    assertThat(board.castle).isEmpty()
    assertThat(board.halfmoveClock).isEqualTo(4)
    assertThat(board.fullmoveClock).isEqualTo(25)
  }

  @Test
  fun `halfmoveclock should be 0 after capture`() {
    val board =
      Board(FENData("8/8/8/8/8/7r/7R/k7", 'b', "", halfmoveClock = 12))
    val startPosition = Position(File.H, Rank.THREE)
    val endPosition = Position(File.H, Rank.TWO)
    val testMove = Move(startPosition, endPosition)
    board.makeMove(testMove)
    assertThat(board.halfmoveClock).isEqualTo(0)
  }

  @Test
  fun `reset halfmoveclock after pawn move, expecting halfmoveclock to be 0`() {
    val board =
      Board(FENData("8/8/8/8/8/1p6/8/k7", 'b', "", halfmoveClock = 12))
    val startPosition = Position(File.B, Rank.THREE)
    val endPosition = Position(File.B, Rank.TWO)
    val testMove = Move(startPosition, endPosition)
    board.makeMove(testMove)
    assertThat(board.halfmoveClock).isEqualTo(0)
  }

  @Test
  fun `increase halfmoveclock expecting halfmoveclock to be 13 `() {
    val board =
      Board(FENData("r7/8/8/8/8/8/8/k7", 'b', "", halfmoveClock = 12))
    val startPosition = Position(File.A, Rank.EIGHT)
    val endPosition = Position(File.A, Rank.SEVEN)
    val testMove = Move(startPosition, endPosition)
    board.makeMove(testMove)
    assertThat(board.halfmoveClock).isEqualTo(13)
  }

  @Test
  fun `increase fullmove clock expecting fullmove to be 13 `() {
    val board =
      Board(FENData("r7/8/8/8/8/8/8/k7", 'b', "", fullmoveClock = 12))
    val startPosition = Position(File.A, Rank.EIGHT)
    val endPosition = Position(File.A, Rank.SEVEN)
    val testMove = Move(startPosition, endPosition)
    board.makeMove(testMove)
    assertThat(board.fullmoveClock).isEqualTo(13)
  }

  @Test
  fun `do not increase fullmove clock expecting fullmove to be 12 `() {
    val board =
      Board(FENData("R7/8/8/8/8/8/8/K7", 'w', "", fullmoveClock = 12))
    val startPosition = Position(File.A, Rank.EIGHT)
    val endPosition = Position(File.A, Rank.SEVEN)
    val testMove = Move(startPosition, endPosition)
    board.makeMove(testMove)
    assertThat(board.fullmoveClock).isEqualTo(12)
  }

  @Test
  fun `Piece movement, rook move e4 to e8 + no exception thrown `() {
    val board = Board(FENData("8/8/8/8/4R3/8/8/K7", 'w', ""))
    val startPosition = Position(File.E, Rank.FOUR)
    val endPosition = Position(File.E, Rank.EIGHT)
    val testMove = Move(startPosition, endPosition)

    shouldNotThrowAny {
      board.makeMove(testMove)
    }
  }

  @Test
  fun `Invalid board creation`() {
    assertThatThrownBy {
      Board(FENData("rnbqkbnr/pppppppp/8/8/8/7/PPPPPPPP/RNBQKBNR"))
    }.message().isEqualTo("Board must have exactly 64 squares.")

    assertThatThrownBy {
      Board(FENData("K7/8/k7/8/8/8/8/9"))
    }.message().isEqualTo("File iterator should have next element.")
  }

  @Test
  fun `FEN board string creation for default setup`() {
    val board = Board(FENData())
    val fenBoardString = board.generateFENBoardString()
    assertThat(
      fenBoardString,
    ).isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")
  }

  @Test
  fun `FEN board string with custom setup`() {
    val testString = "q4b2/8/8/1Q6/3B4/1PP4K/8/n1n1n1n1"
    val board = Board(FENData(testString))
    val fenBoardString = board.generateFENBoardString()
    assertThat(fenBoardString).isEqualTo(testString)
  }

  @Test
  fun `Piece moves on ally, exception expected`() {
    val board = Board(FENData("K7/8/8/8/8/P7/8/R7", castle = ""))
    val move = Move(Position(File.A, Rank.ONE), Position(File.A, Rank.THREE))
    assertThatThrownBy { board.makeMove(move) }.message()
      .isEqualTo("Cannot move to a square occupied by the same color")
    assertThat(board.generateFENBoardString()).isEqualTo("K7/8/8/8/8/P7/8/R7")
  }

  @Test
  fun `Capture Piece, rook captures pawn, expecting valid move`() {
    val board = Board(FENData("K7/8/8/8/8/p7/8/R7", castle = ""))
    val capturedPieces = CapturedPieces(board.getMap())
    val move = Move(Position(File.A, Rank.ONE), Position(File.A, Rank.THREE))
    assertThat(
      capturedPieces.getCapturedPieces(),
    ).isEqualTo(
      "White's captures: rnbqkbnrppppppp${System.lineSeparator()}Black's captures: NBQBNRPPPPPPPP",
    )
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("K7/8/8/8/8/R7/8/8")
    assertThat(
      capturedPieces.getCapturedPieces(),
    ).isEqualTo(
      "White's captures: rnbqkbnrpppppppp${System.lineSeparator()}Black's captures: NBQBNRPPPPPPPP",
    )
  }
}
