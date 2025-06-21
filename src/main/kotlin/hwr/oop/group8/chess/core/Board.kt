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

class Board(val fen: FEN, val stateHistory: List<Int> = emptyList()) {
  private val map = HashMap<Position, Square>()
  var turn: Color
    private set
  var enPassant: Position?
    private set
  var castle: String
  var halfmoveClock: Int
    private set
  var fullmoveClock: Int
    private set
  val boardAnalyser: BoardAnalyser = BoardAnalyser(this)
  private val castling: Castling = Castling(this)

  init {
    initializeBoardFromFENString() // TODO: BoardFactory class

    turn = fen.getTurn()
    castle = fen.castle
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

  private fun isCapture(move: Move): Boolean =
    !boardAnalyser.isSquareEmpty(move.moves().first().to)

  private fun createPieceOnBoard(type: PieceType, color: Color): Piece =
    when (type) {
      PieceType.PAWN -> Pawn(color, boardAnalyser)
      PieceType.ROOK -> Rook(color, boardAnalyser)
      PieceType.KNIGHT -> Knight(color, boardAnalyser)
      PieceType.BISHOP -> Bishop(color, boardAnalyser)
      PieceType.QUEEN -> Queen(color, boardAnalyser)
      PieceType.KING -> King(color, boardAnalyser)
    }

  private fun applyMoves(move: Move, piece: Piece) {
    move.moves().forEach { applySingleMove(it) }
    if (move.isPromotion()) {
      val toSquare = getSquare(move.moves().first().to)
      val pieceType: PieceType = move.promotesTo()!!
      val promotionPiece = createPieceOnBoard(pieceType, piece.color)
      toSquare.setPiece(promotionPiece)
    }
    enPassant = if (move.isDoublePawnMove()) {
      allowedEnPassantTarget(move as DoublePawnMove)
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
    castling.updatePermission()
  }

  private fun resetHalfMoveClock() {
    halfmoveClock = -1
  }

  fun getPieceAt(position: Position): Piece? =
    boardAnalyser.getPieceAt(position)

  fun findPositionOfPiece(piece: Piece): Position =
    boardAnalyser.findPositionOfPiece(piece)

  fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean> =
    castling.isAllowed(color)

  fun isSquareEmpty(position: Position) = boardAnalyser.isSquareEmpty(position)
  fun getCurrentTurn(): Color = turn
  fun getSquare(position: Position): Square = map.getValue(position)
  fun makeMove(move: Move) {
    boardAnalyser.checkForDraw()
    check(!boardAnalyser.isCheckmate()) {
      "Game is over, checkmate!"
    }

    val piece = getPieceAt(move.moves().first().from)

    checkNotNull(piece) { "There is no piece at ${move.moves().first().from}" }
    check(piece.color == turn) { "It's not your turn" }

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
      boardAnalyser.isMoveCheck(matchingMove),
    ) { "Move would put player in check" }

    if (piece.getType() == PieceType.PAWN || isCapture(move)) {
      resetHalfMoveClock()
    }

    applyMoves(matchingMove, piece)

    if (turn == Color.BLACK) fullmoveClock++
    halfmoveClock++
    turn = turn.invert()
  }

  fun newStateHistory() = stateHistory.plus(FEN.boardStateHash(this))

  fun isCheck(): Boolean = boardAnalyser.isCheck()

  fun allowedEnPassantTarget(move: DoublePawnMove): Position? {
    val currentTurn = getCurrentTurn()
    return if (move.to.hasNextPosition(Direction.LEFT) &&
      getPieceAt(move.to.left())?.color != currentTurn &&
      getPieceAt(
        move.to.left(),
      )?.getType() == PieceType.PAWN
    ) {
      move.skippedPosition()
    } else if (move.to.hasNextPosition(Direction.RIGHT) &&
      getPieceAt(move.to.right())?.color != currentTurn &&
      getPieceAt(
        move.to.right(),
      )?.getType() == PieceType.PAWN
    ) {
      move.skippedPosition()
    } else {
      null
    }
  }

  fun isPositionThreatened(currentPlayer: Color, position: Position): Boolean =
    boardAnalyser.isPositionThreatened(currentPlayer, position)

  fun getMap(): HashMap<Position, Square> = map
}
