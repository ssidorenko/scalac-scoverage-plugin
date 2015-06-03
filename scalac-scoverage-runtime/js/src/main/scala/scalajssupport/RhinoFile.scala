package scalajssupport

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

import js.Dynamic.{ global => g, newInstance => jsnew }

@JSName("Packages.java.io.File")
class NativeRhinoFile(path: String, child: String) extends js.Object {
  def this(path: String) = this("", path)

  def delete(): Unit = js.native

  def getAbsolutePath(): Any = js.native

  def getName(): Any = js.native

  def getPath(): Any = js.native

  def isDirectory(): Boolean = js.native

  def length(): js.Any = js.native

  def mkdirs(): Unit = js.native

  def listFiles(): js.Array[NativeRhinoFile] = js.native
}

class RhinoFile(_file: NativeRhinoFile) extends JsFile {
  def this(path: String) = this(new NativeRhinoFile(path))

  def this(path: String, child: String) = {
    this((new NativeRhinoFile(path, child)))
  }

  def delete(): Unit = debug("delete")(_file.delete())

  def getAbsolutePath(): String = debug("getAbsolute")("" + _file.getAbsolutePath())

  def getName(): String = debug("getname")("" + _file.getName())

  def getPath(): String = debug("getPath")
    {
      "" + _file.getPath() // Rhino bug: doesn't seem to actually returns a string, we have to convert it ourselves
    }

  def isDirectory(): Boolean = debug("isDirectory")(_file.isDirectory())

  def mkdirs(): Unit = debug("mkdirs")(_file.mkdirs())

  def listFiles(): Array[File] = {
    println("ok")

    val files = _file.listFiles()
    println(files(0))
    val filesArray = new Array[File](files.length)
    for ((item, i) <- filesArray.zipWithIndex) {
      filesArray(i) = new File("" + files(i).getAbsolutePath())
    }
    println("fin")
    filesArray
  }

  def readFile(): String = debug("readfile"){
     //val scanner = jsnew(g.Packages.java.util.Scanner)(_file).useDelimiter("\\Z").next().toString
     val fis = jsnew(g.Packages.java.io.FileInputStream)(_file)
     val data = g.Packages.java.lang.reflect.Array.newInstance(
         g.Packages.java.lang.Byte.TYPE, _file.length()
     )
     fis.read(data)
     fis.close()
     println(data)
     println("ook")
     println(data.toString())
     println("ookk")
     "" + jsnew(g.Packages.java.lang.String)(data)
  }

  def debug[A](msg: String)(b: => A): A = {
    println("debut " + msg)
    val r = b
    println("fin" + msg)
    r
  }
}


private[scalajssupport] object RhinoFile {
  def write(path: String, data: String, mode: String) = debug("write"){
    val outputstream = jsnew(g.Packages.java.io.FileOutputStream)(path, mode == "a")
    val jString = jsnew(g.Packages.java.lang.String)(data)
    outputstream.write(jString.getBytes())
  }
  def debug[A](msg: String)(b: => A): A = {
    println("debut " + msg)
    val r = b
    println("fin" + msg)
    r
  }
}