package hwr.oop.group8.chess.core

import hwr.oop.group8.chess.persistence.FENData
import hwr.oop.group8.chess.piece.Piece

class Board(val fenData: FENData) : BoardInspector {
  private val map = HashMap<Position, Square>()
  var turn: Color
  val enPassant: String
  var castle: String
  var halfmoveClock: Int
  var fullmoveClock: Int
  val boardLogic: BoardLogic = BoardLogic(this)

  init {
    for (rank in 1..8) {
      var fileCounter = 'a'
      fenData.getRank(rank).forEach { fileChar ->
        if (fileChar.isDigit()) {
          repeat(fileChar.digitToInt()) {
            map.put(Position(fileCounter, rank), Square(null))
            fileCounter++
          }
        } else {
          map.put(
            Position(fileCounter, rank),
            Square(FENData.createPieceOnBoard(fileChar, this))
          )
          fileCounter++
        }
      }
    }
    check(map.size == 64) { "Board must have exactly 64 squares." }

    turn = fenData.getTurn()
    castle = fenData.castle
    enPassant = fenData.enPassant
    halfmoveClock = fenData.halfmoveClock
    fullmoveClock = fenData.fullmoveClock
    check(!isCheckmate()) {
      "Game is over, checkmate!"
    }
  }

  fun getSquare(position: Position): Square {
    return map.getValue(position)
  }

  override fun getPieceAt(position: Position): Piece? {
    return getSquare(position).getPiece()
  }

  override fun isSquareEmpty(position: Position): Boolean {
    return getSquare(position).getPiece() == null
  }

  override fun findPositionOfPiece(piece: Piece): Position {
    return map.filterValues { it.getPiece() === piece }.keys.first()
  }

  fun makeMove(move: Move) {
    val fromSquare = getSquare(move.from)
    val toSquare = getSquare(move.to)
    val specialMove = move.specialMove
    val piece = fromSquare.getPiece()

    checkNotNull(piece)
    check(piece.color == turn)
    { "It's not your turn" }

    require(piece.color != toSquare.getPiece()?.color)
    { "Cannot move to a square occupied by the same color" }

    check(piece.getValidMoveDestinations().contains(move))
    { "Invalid move for piece ${piece::class.simpleName} from ${move.from} to ${move.to}" }

    check(isMoveCheck(move))
    { "Move would put player in check" }
    if (!isSquareEmpty(move.to)) resetHalfMoveClock()
    //apply specialmove
    if (!move.specialMove.isEmpty()) {
      val specialToSquare = getSquare(specialMove.first().to)
      val specialFromSquare = getSquare(specialMove.first().from)
      val specialMovePiece = getPieceAt(specialMove.first().from)
      specialToSquare.setPiece(specialMovePiece)
      specialFromSquare.setPiece(null)
    }

    // apply standardmove
    toSquare.setPiece(piece)
    fromSquare.setPiece(null)
    updateCastlingPermission()
    piece.saveMoveToHistory(move)
    if (turn == Color.BLACK) fullmoveClock++
    halfmoveClock++
    turn = turn.invert()
  }

  private fun isCheckmate(): Boolean = boardLogic.isCheckmate()

  private fun isMoveCheck(move: Move): Boolean = boardLogic.isMoveCheck(move)

  fun isCheck(): Boolean = boardLogic.isCheck()

  override fun isCastlingAllowed(color: Color): Pair<Boolean, Boolean> =
    boardLogic.isCastlingAllowed(color)


  override fun getCurrentTurn(): Color = turn

  override fun isPositionThreatened(
    currentPlayer: Color,
    position: Position,
  ): Boolean = boardLogic.isPositionThreatened(currentPlayer, position)

  override fun resetHalfMoveClock() {
    halfmoveClock = -1
  }

  private fun updateCastlingPermission() = boardLogic.updateCastlingPermission()

  fun getMap(): HashMap<Position, Square> = map

  fun generateFENBoardString(): String = fenData.generateFENBoardString(this)


  fun getFENData(): FENData = fenData.getFENData(this)
}