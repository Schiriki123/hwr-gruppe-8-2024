package hwr.oop.group8.chess.persistence

object PersistenceError {
  class CouldNotDeleteGameException(message: String) : Exception(message)
  class CouldNotLoadGameException(message: String) : Exception(message)
  class CouldNotSaveGameException(message: String) : Exception(message)
}
