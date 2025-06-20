package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.core.move.DoublePawnMove
import hwr.oop.group8.chess.core.move.Move
import hwr.oop.group8.chess.core.move.SingleMove
import hwr.oop.group8.chess.core.piece.Bishop
import hwr.oop.group8.chess.core.piece.Knight
import hwr.oop.group8.chess.core.piece.Piece
import hwr.oop.group8.chess.core.piece.PieceType
import hwr.oop.group8.chess.core.piece.Queen
import hwr.oop.group8.chess.core.piece.Rook
import hwr.oop.group8.chess.persistence.FEN

class Board(
  val fen: FEN,
  val stateHistory: MutableList<Int> = mutableListOf(),
) : BoardInspector,
  BoardInspectorEnPassant {
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
  private val boardAnalyser: BoardAnalyser = BoardAnalyser(this)
  private val enPassantAnalyser: EnPassantAnalyser = EnPassantAnalyser(this)
  private val castlingLogic: CastlingLogic = CastlingLogic(this)

  init {
    initializeBoardFromFENString() // TODO: BoardFactory class

    turn = fen.getTurn()
    castle = fen.castle
    enPassant = fen.enPassant()
    halfmoveClock = fen.halfmoveClock
    fullmoveClock = fen.fullmoveClock
    // TODO: Move to makeMove
    checkForDraw()
    check(!boardAnalyser.isCheckmate()) {
      "Game is over, checkmate!"
    }
  }

  private fun initializeBoardFromFENString() {
    for (rank in Rank.entries) {
      val fileIterator = File.entries.iterator()
      fen.getRank(rank).forEach { character ->
        if (character.isDigit()) {
          repeat(character.digitToInt()) {
            populateRank(fileIterator, rank, null)
          }
        } else {
          val piece = FEN.createPieceOnBoard(character, this)
          populateRank(fileIterator, rank, piece)
        }
      }
    }
    check(map.size == 64) { "Board must have exactly 64 squares." }
  }

  private fun populateRank(
    fileIterator: Iterator<File>,
    rank: Rank,
    piece: Piece?,
  ) {
    check(fileIterator.hasNext()) { "File iterator should have next element." }
    val position = Position(fileIterator.next(), rank)
    map[position] = Square(piece)
  }

  fun getSquare(position: Position): Square = map.getValue(position)

  override fun getPieceAt(position: Position): Piece? =
    getSquare(position).getPiece()

  override fun findPositionOfPiece(piece: Piece): Position = map.filterValues {
    it.getPiece() === piece
  }.keys.first()

  fun makeMove(move: Move) {
    val piece = getPieceAt(move.moves().first().from)

    checkNotNull(piece)
    check(piece.color == turn) { "It's not your turn" }

    val matchingMove = piece.getValidMoveDestinations().find { validMoves ->
      validMoves.moves().first() == move.moves().first() &&
        validMoves.promotesTo() == move.promotesTo()
    }

    checkNotNull(matchingMove) {
      "Invalid move for piece ${piece::class.simpleName} from ${
        move.moves().first().from
      } to ${move.moves().first().to}"
    }

    check(isMoveCheck(matchingMove)) { "Move would put player in check" }

    if (piece.getType() == PieceType.PAWN || isCapture(move)) {
      resetHalfMoveClock()
    }

    applyMoves(matchingMove, piece)

    if (turn == Color.BLACK) fullmoveClock++
    halfmoveClock++
    stateHistory.add(generateFENBoardString().hashCode())
    turn = turn.invert()
  }

  private fun checkForDraw() {
    if (halfmoveClock >= 50) {
      throw IllegalStateException("Game is draw due to the 50-move rule.")
    }
    if (isRepetitionDraw()) {
      throw IllegalStateException("Game is draw due to threefold repetition.")
    }
  }

  fun isRepetitionDraw(): Boolean = stateHistory.groupBy { it }
    .any { it.value.size >= 3 }

  private fun isCapture(move: Move): Boolean =
    !isSquareEmpty(move.moves().first().to)

  private fun generatePromotionPiece(type: PieceType, color: Color): Piece =
    when (type) {
      // TODO: boardInspector could be passed as a parameter
      PieceType.QUEEN -> Queen(color, this)
      PieceType.ROOK -> Rook(color, this)
      PieceType.BISHOP -> Bishop(color, this)
      PieceType.KNIGHT -> Knight(color, this)
      else -> {
        throw IllegalArgumentException("Invalid promotion piece type: $type")
      }
    }

  private fun applyMoves(move: Move, piece: Piece) {
    move.moves().forEach { applySingleMove(it) }
    if (move.isPromotion()) {
      val toSquare = getSquare(move.moves().first().to)
      val pieceType = move.promotesTo()
      requireNotNull(pieceType)
      val promotionPiece = generatePromotionPiece(pieceType, piece.color)
      toSquare.setPiece(promotionPiece)
    }
    if (move.isDoublePawnMove()) {
      enPassantAnalyser.updateAllowedEnPassant(move as DoublePawnMove)
    } else {
      enPassant = null
    }
    if (move.enPassantCapture() != null) {
      getSquare(move.enPassantCapture()!!).setPiece(null)
    }
  }

  private fun applySingleMove(singleMove: SingleMove) {
    val toSquare = getSquare(singleMove.to)
    val fromSquare = getSquare(singleMove.from)
    val piece = fromSquare.getPiece()
    checkNotNull(piece) { "No piece found at ${singleMove.from}" }
    toSquare.setPiece(piece)
    fromSquare.setPiece(null)
    castlingLogic.updateCastlingPermission()
  }

  private fun isMoveCheck(move: Move): Boolean = boardAnalyser.isMoveCheck(move)

  fun isCheck(): Boolean = boardAnalyser.isCheck()

  override fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean> =
    castlingLogic.isCastlingAllowed(color)

  override fun getCurrentTurn(): Color = turn
  override fun accessEnPassant(): Position? = enPassant

  override fun setEnPassant(position: Position?) {
    enPassant = position
  }

  fun isPositionThreatened(currentPlayer: Color, position: Position): Boolean =
    boardAnalyser.isPositionThreatened(currentPlayer, position)

  private fun resetHalfMoveClock() {
    halfmoveClock = -1
  }

  fun getMap(): HashMap<Position, Square> = map

  fun generateFENBoardString(): String = FEN.generateFENBoardString(this)
}
