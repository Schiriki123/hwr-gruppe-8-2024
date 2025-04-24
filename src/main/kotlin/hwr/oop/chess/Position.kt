package hwr.oop.chess

data class Position(val rank:Int, val file: Char)
{
    fun getPosition(): String{
        return "${file}${rank}"
    }
}
