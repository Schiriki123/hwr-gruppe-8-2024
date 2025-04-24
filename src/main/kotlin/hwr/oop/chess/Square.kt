package hwr.oop.chess

class Square(val file: Char,val rank: Int,  val piece : Piece) {
    fun getPosition(): String{
        return "${file}${rank}"
    }
}