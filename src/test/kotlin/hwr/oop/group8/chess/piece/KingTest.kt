package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class KingTest : AnnotationSpec() {
  @Test
  fun `Test char representation`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/8"))
    val whiteKing = King(Color.WHITE, boardInspector)
    val blackKing = King(Color.BLACK, boardInspector)
    assertThat(whiteKing.getChar()).isEqualTo('K')
    assertThat(blackKing.getChar()).isEqualTo('k')
  }

  @Test
  fun `Test king movement block path`() {
    val board = Board(FENData("8/B7/K7/8/8/8/8/8"))
    val move = Move(Position('a', 6), Position('a', 7))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/B7/K7/8/8/8/8/8")
  }

  @Test
  fun `Test king movement on empty board`() {
    val board = Board(FENData("8/8/8/3K4/8/8/8/8"))
    // King moves left
    var move = Move(Position('d', 5), Position('c', 5))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/2K5/8/8/8/8")

    //King moves right
    move = Move(Position('c', 5), Position('d', 5))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/3K4/8/8/8/8")

    //King moves down
    move = Move(Position('d', 5), Position('d', 4))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3K4/8/8/8")

    //King moves up
    move = Move(Position('d', 4), Position('d', 5))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/3K4/8/8/8/8")

    //King moves top right
    move = Move(Position('d', 5), Position('e', 6))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/4K3/8/8/8/8/8")

    //King moves top left
    move = Move(Position('e', 6), Position('d', 7))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/3K4/8/8/8/8/8/8")

    //King moves bottom left
    move = Move(Position('d', 7), Position('c', 6))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/2K5/8/8/8/8/8")

    //King moves bottom right
    move = Move(Position('c', 6), Position('d', 5))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/3K4/8/8/8/8")
  }

  @Test
  fun `Test invalid double move`() {
    val board = Board(FENData("8/8/K7/8/8/8/8/8"))
    val move = Move(Position('a', 6), Position('a', 4))
    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/K7/8/8/8/8/8")
  }

  @Test
  fun `Test King movement to capture`() {
    val board = Board(FENData("8/p7/1K6/8/8/8/8/8"))
    val move = Move(Position('b', 6), Position('a', 7))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/K7/8/8/8/8/8/8")
  }

  @Test
  @Ignore
  fun `Test King castle`() {
    var board = Board(FENData("8/8/8/8/8/8/8/R3K2R"))
    //King side castle
    var move = Move(Position('e', 6), Position('g', 1))
    board.makeMove(move)
    move = Move(Position('h', 1), Position('f', 1))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/8/R5RK1")

    //Queen side castle
    board = Board(FENData("8/8/8/8/8/8/8/R3K2R"))
    move = Move(Position('e', 6), Position('c', 1))
    board.makeMove(move)
    move = Move(Position('a', 1), Position('d', 1))
    board.makeMove(move)
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/8/2KR3R")
  }

}
