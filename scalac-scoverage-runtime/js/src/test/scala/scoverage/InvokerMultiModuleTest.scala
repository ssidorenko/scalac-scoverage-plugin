package scoverage

import utest._

object InvokerTest extends TestSuite {
  val measurementDir = Array(
    new Platform.File("target/invoker-test.measurement0"),
    new Platform.File("target/invoker-test.measurement1")
  )

  private def deleteMeasurementFiles(): Unit = {
    measurementDir.foreach((md) => {
      if (md.isDirectory)
        md.listFiles().foreach(_.delete())
    })
  }
  // TODO check why test do not pass
  def tests = TestSuite {
    'Invoker{
      deleteMeasurementFiles();
      measurementDir.foreach(_.mkdirs())
      val testIds: Set[Int] = (1 to 10).toSet
      testIds.map { i: Int => Invoker.invoked(i, measurementDir(i % 2).getPath) }

      // Verify measurements went to correct directory
      val measurementFiles0 = Invoker.findMeasurementFiles(measurementDir(0))
      
      val idsFromFile0 = Invoker.invoked(measurementFiles0).toSet
      
      idsFromFile0 == testIds.filter { i: Int => i % 2 == 0 }

      val measurementFiles1 = Invoker.findMeasurementFiles(measurementDir(0))
      val idsFromFile1 = Invoker.invoked(measurementFiles1).toSet
      idsFromFile1 == testIds.filter { i: Int => i % 2 == 1 }

      deleteMeasurementFiles()
      measurementDir.foreach(_.delete)
    }
  }
}

