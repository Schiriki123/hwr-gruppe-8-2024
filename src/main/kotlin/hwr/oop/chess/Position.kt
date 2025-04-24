package hwr.oop.chess

data class Position( val file: Char,val rank:Int)
{
    fun getPosition(): String{
        return "${file}${rank}"
    }
}
