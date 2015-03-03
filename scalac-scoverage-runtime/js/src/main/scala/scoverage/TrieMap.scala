package scoverage

import scala.collection.mutable.HashMap
import scala.collection.generic.{CanBuildFrom, MutableMapFactory}

class TrieMap[K, V] extends HashMap[K, V] {}

object TrieMap  { //extends MutableMapFactory[CrossMap] {
	//implicit def canBuildFrom[A, B]: CanBuildFrom[HashMap[A, B], (A, B), HashMap[A, B]] = new MapCanBuildFrom[A, B]
	def empty[A, B]: TrieMap[A, B] = new TrieMap[A, B]
}