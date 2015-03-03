package scoverage

import utest._

object InvokerTest extends TestSuite {


  def tests = TestSuite {
    'Invoker {
      val measurementDir = new File("target/invoker-test.measurement")
      measurementDir.mkdirs()
      Invoker.invoked(1, measurementDir.toString)
      measurementDir.delete()

    }
  }
}

