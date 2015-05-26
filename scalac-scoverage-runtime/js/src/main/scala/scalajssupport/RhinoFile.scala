package scalajssupport

import scala.scalajs.js
import js.Dynamic.{ global => g, newInstance => jsnew }

class RhinoFile(path: String) extends js.Object with JsFile {
  val _file = jsnew(g.Packages.java.io.File)(path)

  def this(path: String, child: String) = {
    this(jsnew(g.Packages.java.io.File)(path, child).getPath().asInstanceOf[String])
  }

  def delete(): Unit = {
    _file.delete()
  }

  def getAbsolutePath(): String = {
    _file.getAbsolutePath().asInstanceOf[String]
  }

  def getName(): String = {
    _file.getName().asInstanceOf[String]
  }

  def getPath(): String = {
    _file.getPath().asInstanceOf[String]
  }

  def isDirectory(): Boolean = {
    _file.isDirectory().asInstanceOf[Boolean]
  }

  def mkdirs(): Unit = {
    _file.getAbsolutePath().asInstanceOf[String]
  }

  def listFiles(): Array[File] = {
    val files = _file.listFiles().asInstanceOf[js.Dynamic].toArray
    val filesArray = new Array[File](files.length.asInstanceOf[Int])
    for ((item, i) <- filesArray.zipWithIndex) {
      filesArray(i) = new File((new RhinoFile(this.getPath(), files(i).getPath().asInstanceOf[String])).getPath())
    }
    filesArray
  }

  def readFile(): String = {
     jsnew(g.Packages.java.util.Scanner)(_file).useDelimiter("\\Z").next().asInstanceOf[String]
  }

}


private[scalajssupport] object RhinoFile {
}