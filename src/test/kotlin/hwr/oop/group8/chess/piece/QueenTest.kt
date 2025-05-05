package hwr.oop.group8.chess.piece

import hwr.oop.group8.chess.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class QueenTest : AnnotationSpec() {
    fun `Test char representation`() {
        val whiteQueen = Rook(Color.WHITE)
        val blackQueen = Rook(Color.BLACK)
        assertThat(whiteQueen.getChar()).isEqualTo('R')
        assertThat(blackQueen.getChar()).isEqualTo('r')
    }

    @Test
    fun `Test Queen movement on empty board`() {
        val board = Board(FENData("Q7/8/8/8/8/8/8/8"))
        // Queen moves multiple squares down
        var move = Move(Position('a', 8), Position('a', 2))
        board.makeMove(move)
        assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/Q7/8")

        //Queen moves multiple squares up
        move = Move(Position('a', 2), Position('a', 8))
        board.makeMove(move)
        assertThat(board.generateFENBoardString()).isEqualTo("Q7/8/8/8/8/8/8/8")
        //Queen moves multiple squares down right
        move = Move(Position('a', 8), Position('h', 1))
        board.makeMove(move)
        assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/8/7Q")

        //Queen moves multiple squares up left
        move = Move(Position('h', 1), Position('d', 5))
        board.makeMove(move)
        assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/3Q4/8/8/8/8")

        //Queen moves multiple squares up right
        move = Move(Position('d', 5), Position('g', 8))
        board.makeMove(move)
        assertThat(board.generateFENBoardString()).isEqualTo("6Q1/8/8/8/8/8/8/8")

        //Queen moves multiple squares down left
        move = Move(Position('g', 8), Position('a', 2))
        board.makeMove(move)
        assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/Q7/8")
    }

    @Test
    fun `Test invalid Queen movement`() {
        val board = Board(FENData("Q7/8/8/8/8/8/8/8"))
        val move = Move(Position('a', 8), Position('b', 2))
        assertThatThrownBy { board.makeMove(move) }
            .hasMessageContaining("Invalid move: Is not straight or diagonal")
    }

    @Test
    fun `Test Queen movement with straight path blocked by pawn`() {
        val board = Board(FENData("R7/8/8/8/8/8/P7/8"))
        val move = Move(Position('a', 8), Position('a', 1))
        assertThatThrownBy { board.makeMove(move) }
            .hasMessageContaining("Invalid move for piece Rook from a8 to a1")
    }

    @Test
    fun `Test Queen capture with straight moves`(){
        val board = Board(FENData("Q7/8/8/8/8/8/p7/8"))
        val move = Move(Position('a', 8), Position('a', 2))
        board.makeMove(move)

        assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/Q7/8")
    }

    @Test
    fun `Test diagonal Queen movement with blocked path`() {
        val board = Board(FENData("Q7/8/8/8/4R3/8/8/8"))
        val move = Move(Position('a', 8), Position('g', 2))
        assertThatThrownBy { board.makeMove(move) }
            .hasMessageContaining("Invalid move for piece Queen from a8 to g2")
    }

    @Test
    fun `Test Queen capture with diagonal moves`(){
        val board = Board(FENData("Q7/8/8/8/8/8/6p1/8"))
        val move = Move(Position('a', 8), Position('g', 2))
        board.makeMove(move)

        assertThat(board.generateFENBoardString()).isEqualTo("8/8/8/8/8/8/6Q1/8")
    }

}