package hwr.oop.group8.chess.core.exceptions

class InvalidBoardSizeException :
  Exception("Board must have exactly 64 squares.")

class FileToShortException :
  Exception("File iterator should have next element.")
