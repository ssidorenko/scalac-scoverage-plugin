package scoverage
// This class is temporary, getId will be implemented in scalajs soon

class Thread {
  def getId: Int = 1
}

object Thread {
  val currentThread = new Thread
}