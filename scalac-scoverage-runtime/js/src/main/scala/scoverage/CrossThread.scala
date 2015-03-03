package scoverage

class CrossThread {
  def getId: Int = 0
}

object CrossThread {
  val currentThread = new CrossThread
}