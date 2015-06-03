package scalajssupport

import scala.scalajs.js
import js.Dynamic.{global => g}

class File(path: String) {
  import File._

  val _file = jsEnv match {
    case Phantom => new PhantomFile(path)
    case Rhino => new RhinoFile(path)
    case Node => new NodeFile(path)
  }  

  def this(path: String, child: String) = {
    this(File.pathJoin(path, child))
  }

  def delete(): Unit = {
    _file.delete()
  }
  def getAbsolutePath(): String = {
    _file.getAbsolutePath()
  }

  def getName(): String = {
    _file.getName()
  }

  def getPath(): String = {
    _file.getPath()
  }

  def isDirectory(): Boolean = {
    _file.isDirectory()
  }

  def mkdirs(): Unit = {
    _file.mkdirs()
  }

  def listFiles(): Array[File]= {
    _file.listFiles().toArray
  }

  def listFiles(filter: FileFilter): Array[File] = {
    _file.listFiles().filter(filter.accept).toArray
  }

  def readFile(): String = {
    _file.readFile()
  }
}


object File {
  sealed trait JSEnv
  case object Rhino extends JSEnv
  case object Node extends JSEnv
  case object Phantom extends JSEnv
  
  val jsEnv = if (js.Dynamic.global.hasOwnProperty("Packages").asInstanceOf[Boolean])
    Rhino
  else if(!js.Dynamic.global.hasOwnProperty("window").asInstanceOf[Boolean])
    Node
  else if(js.Dynamic.global.hasOwnProperty("_phantom").asInstanceOf[Boolean])
    Phantom

  // Factorize this

  def pathJoin(path: String, child: String): String = 
    jsEnv match {
      case Phantom => PhantomFile.pathJoin(path, child)
      case Node => NodeFile.nodePath.join(path, child)
      case Rhino => (new RhinoFile(path, child)).getPath()
    }

  def write(path: String, data: String, mode: String = "a") = 
    jsEnv match {
      case Phantom => PhantomFile.write(path, data, mode)
      case Node => NodeFile.fs.writeFileSync(path, data, js.Dynamic.literal(flag=mode))
      case Rhino => RhinoFile.write(path, data, mode)
    }
}