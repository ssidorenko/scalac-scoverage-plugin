package scoverage

import scala.collection.mutable.HashMap
import scala.collection.generic.{CanBuildFrom, MutableMapFactory}

class CrossMap[K, V] extends HashMap[K, V] {}

object CrossMap  { //extends MutableMapFactory[CrossMap] {
	//implicit def canBuildFrom[A, B]: CanBuildFrom[HashMap[A, B], (A, B), HashMap[A, B]] = new MapCanBuildFrom[A, B]
	def empty[A, B]: CrossMap[A, B] = new CrossMap[A, B]
}