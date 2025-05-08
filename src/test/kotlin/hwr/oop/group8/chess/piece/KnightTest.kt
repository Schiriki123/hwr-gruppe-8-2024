package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class KnightTest : AnnotationSpec() {

  @Test
  fun `Test char representation`() {
    val boardInspector = Board(FENData("8/8/8/8/8/8/8/8"))
    val whiteKnight = Knight(Color.WHITE, boardInspector)
    val blackKnight = Knight(Color.BLACK, boardInspector)
    assertThat(whiteKnight.getChar()).isEqualTo('N')
    assertThat(blackKnight.getChar()).isEqualTo('n')
  }

  @Test
  fun `Test Knight movement`() {
    //Knight move from d4 to f5
    var board = Board(FENData("8/8/8/8/3N4/8/8/8"))
    var move = Move(Position('d', 4), Position('f', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/8/8/8/8")

    //Knight move from d4 to e6
    board = Board(FENData("8/8/8/8/3N4/8/8/8"))
    move = Move(Position('d', 4), Position('e', 6))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/4N3/8/8/8/8/8")

    //Knight move from d4 to e2
    board = Board(FENData("8/8/8/8/3N4/8/8/8"))
    move = Move(Position('d', 4), Position('e', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/4N3/8")

    //Knight move from d4 to b3
    board = Board(FENData("8/8/8/8/3N4/8/8/8"))
    move = Move(Position('d', 4), Position('b', 3))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/1N6/8/8")

    //Knight move from d4 to c2
    board = Board(FENData("8/8/8/8/3N4/8/8/8"))
    move = Move(Position('d', 4), Position('c', 2))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/2N5/8")

    //Knight move from d4 to b5
    board = Board(FENData("8/8/8/8/3N4/8/8/8"))
    move = Move(Position('d', 4), Position('b', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/1N6/8/8/8/8")

    //Knight move from d4 to c6
    board = Board(FENData("8/8/8/8/3N4/8/8/8"))
    move = Move(Position('d', 4), Position('c', 6))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/2N5/8/8/8/8/8")

    //Knight move from d4 to f3
    board = Board(FENData("8/8/8/8/3N4/8/8/8"))
    move = Move(Position('d', 4), Position('f', 3))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/5N2/8/8")

    // Knight move from a3 to b5
    board = Board(FENData("8/8/8/8/8/N7/8/8"))
    move = Move(Position('a', 3), Position('b', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/1N6/8/8/8/8")
  }

  @Test
  fun `Test Knight capture`() {
    val board = Board(FENData("8/8/8/5n2/3N4/8/8/8"))
    val move = Move(Position('d', 4), Position('f', 5))
    board.makeMove(move)

    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/8/8/8/8")
  }

  @Test
  fun `Test Knight end-position blocked`() {
    val board = Board(FENData("8/8/8/5N2/3N4/8/8/8"))
    val move = Move(Position('d', 4), Position('f', 5))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/5N2/3N4/8/8/8")
  }

  @Test
  fun `Test Knight invalid moves`() {
    // Knight move from d4 to d2
    var board = Board(FENData("8/8/8/8/3N4/8/8/8"))
    var move = Move(Position('d', 4), Position('d', 2))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3N4/8/8/8")

    // Knight move from d4 to e5
    board = Board(FENData("8/8/8/8/3N4/8/8/8"))
    move = Move(Position('d', 4), Position('f', 6))

    assertThatThrownBy { board.makeMove(move) }
    assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/3N4/8/8/8")
  }

  @Test
  fun `Test knight movement set generation with overflow for rank & file`() {
    val board = Board(FENData("8/6n1/8/7p/8/8/8/8", 'b'))
    val validMoveDestinationsOfKnight =
      board.getPieceAt(Position('g', 7))!!.getValidMoveDestinations()

    assertThat(validMoveDestinationsOfKnight).containsExactly(
      Position('e', 8),
      Position('e', 6),
      Position('f', 5)
    )
  }

  @Test
  fun `Test knight movement set generation with lower overflow for rank & file`() {
    val board = Board(FENData("8/8/8/8/3p4/8/2N5/R7"))
    val validMoveDestinationOfKnight =
      board.getPieceAt(Position('c', 2))!!.getValidMoveDestinations()

    assertThat(validMoveDestinationOfKnight).containsExactly(
      Position('e', 3),
      Position('e', 1),
      Position('a', 3),
      Position('d', 4),
      Position('b', 4)
    )
  }
}
