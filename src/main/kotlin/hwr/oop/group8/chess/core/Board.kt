package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.move.DoublePawnMove
import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.core.piece.Bishop
import hwr.oop.group8.chess.core.piece.King
import hwr.oop.group8.chess.core.piece.Knight
import hwr.oop.group8.chess.core.piece.Pawn
import hwr.oop.group8.chess.core.piece.Piece
import hwr.oop.group8.chess.core.piece.PieceType
import hwr.oop.group8.chess.core.piece.Queen
import hwr.oop.group8.chess.core.piece.Rook
import hwr.oop.group8.chess.persistence.FEN

class Board private constructor(val fen: FEN, val stateHistory: List<Int>) {
  private val map = HashMap<Position, Square>()
  var turn: Color
    private set
  var enPassant: Position?
    private set
  var halfmoveClock: Int
    private set
  var fullmoveClock: Int
    private set
  val analyser: BoardAnalyser = BoardAnalyser(this, fen.castle)

  companion object {
    fun factory(fen: FEN, stateHistory: List<Int> = emptyList()): Board =
      Board(fen, stateHistory)
  }

  init {
    initializeBoardFromFENString() // TODO: BoardFactory class

    turn = fen.getTurn()
    enPassant = fen.enPassant()
    halfmoveClock = fen.halfmoveClock
    fullmoveClock = fen.fullmoveClock
  }

  private fun initializeBoardFromFENString() {
    for (rank in Rank.entries) {
      val fileIterator = File.entries.iterator()
      fen.getRank(rank).forEach { character ->
        if (character.isDigit()) {
          repeat(character.digitToInt()) {
            populateSquare(fileIterator, rank, null)
          }
        } else {
          val pieceDef = FEN.convertChar(character)
          val piece = createPieceOnBoard(pieceDef.first, pieceDef.second)
          populateSquare(fileIterator, rank, piece)
        }
      }
    }
    check(map.size == 64) { "Board must have exactly 64 squares." }
  }

  private fun populateSquare(
    fileIterator: Iterator<File>,
    rank: Rank,
    piece: Piece?,
  ) {
    check(fileIterator.hasNext()) { "File iterator should have next element." }
    val position = Position(fileIterator.next(), rank)
    map[position] = Square(piece)
  }

  private fun createPieceOnBoard(type: PieceType, color: Color): Piece =
    when (type) {
      PieceType.PAWN -> Pawn(color, analyser)
      PieceType.ROOK -> Rook(color, analyser)
      PieceType.KNIGHT -> Knight(color, analyser)
      PieceType.BISHOP -> Bishop(color, analyser)
      PieceType.QUEEN -> Queen(color, analyser)
      PieceType.KING -> King(color, analyser)
    }

  private fun applyMoves(move: Move, piece: Piece) {
    move.moves().forEach { applySingleMove(it) }
    if (move.isPromotion()) {
      val toSquare = getSquare(move.moves().first().to)
      val pieceType: PieceType = move.promotesTo()!!
      val promotionPiece = createPieceOnBoard(pieceType, piece.color())
      toSquare.setPiece(promotionPiece)
    }
    enPassant = if (move.isDoublePawnMove()) {
      analyser.allowedEnPassantTarget(move as DoublePawnMove)
    } else {
      null
    }
    if (move.enPassantCapture() != null) {
      getSquare(move.enPassantCapture()!!).setPiece(null)
    }
  }

  private fun applySingleMove(singleMove: SingleMove) {
    val toSquare = getSquare(singleMove.to)
    val fromSquare = getSquare(singleMove.from)
    val piece = fromSquare.getPiece()
    toSquare.setPiece(piece)
    fromSquare.setPiece(null)
    analyser.castling.updatePermission()
  }

  private fun resetHalfMoveClock() {
    halfmoveClock = -1
  }

  fun castle() = analyser.castling.string()

  fun getSquare(position: Position): Square = map.getValue(position)
  fun makeMove(move: Move) {
    analyser.checkForDraw()
    check(!analyser.isCheckmate()) {
      "Game is over, checkmate!"
    }

    val piece = analyser.getPieceAt(move.moves().first().from)

    checkNotNull(piece) { "There is no piece at ${move.moves().first().from}" }
    check(piece.color() == turn) { "It's not your turn" }

    val matchingMove = piece.getValidMove().find { validMoves ->
      validMoves.moves().first() == move.moves()
        .first() &&
        validMoves.promotesTo() == move.promotesTo()
    }

    checkNotNull(matchingMove) {
      "Invalid move for piece ${piece::class.simpleName} from ${
        move.moves().first().from
      } to ${move.moves().first().to}"
    }

    check(
      analyser.isMoveCheck(matchingMove),
    ) { "Move would put player in check" }

    if (piece.getType() == PieceType.PAWN || analyser.isCapture(move)) {
      resetHalfMoveClock()
    }

    applyMoves(matchingMove, piece)

    if (turn == Color.BLACK) fullmoveClock++
    halfmoveClock++
    turn = turn.invert()
  }

  fun newStateHistory() = stateHistory.plus(FEN.boardStateHash(this))

  fun getMap(): HashMap<Position, Square> = map
}
