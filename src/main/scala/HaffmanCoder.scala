package main

trait NodeT {

  def frequency: Double

  def generateCode: Set[(Int, String)] = generateCode(this, "")

  def generateCode(node: NodeT, code: String): Set[(Int, String)] =
    node match {
      case Leaf(sym, _) => Set((sym, code))
      case Node(l, r) =>
        generateCode(l, code + "1") ++ generateCode(r, code + "0")
    }

}

case class Node(left: NodeT, right: NodeT) extends NodeT {

  val frequency: Double = left.frequency + right.frequency

}

case class Leaf(symbol: Int, frequency: Double) extends NodeT {

}

import scala.collection.mutable.{Set => MSet}
object HaffmanTreeBuilder {

  implicit
  def map2Leaves(frequencyMap: Map[Int, Double]): MSet[NodeT] = {
    val leafSet = frequencyMap.map {
      case (num, freq) => Leaf(num, freq)
    }.toSet

    MSet() ++ leafSet
  }

  def minFrequencyNode(nodes: MSet[NodeT]): NodeT =
    nodes.reduceLeft {
      (a, b) => if (a.frequency < b.frequency) a else b
    }

  def build(nodes: MSet[NodeT]): MSet[NodeT] = {
    if (nodes.size == 1) {
      nodes
    } else {
      val left = minFrequencyNode(nodes)
      nodes.remove(left)

      val right = minFrequencyNode(nodes)
      nodes.remove(right)

      val parent = Node(left, right)
      nodes.add(parent)

      build(nodes)
    }
  }

  def apply(freqMap: Map[Int, Double]): NodeT =
    build(freqMap).head

}

object Main {
  def main(args: Array[String]) = {
    val fmap = Map(
      0 -> 0.04,
      1 -> 0.08,
      2 -> 0.12,
      3 -> 0.25,
      4 -> 0.28,
      5 -> 0.14,
      6 -> 0.06,
      7 -> 0.03)

    val root = HaffmanTreeBuilder(fmap)
    println(root.generateCode)

    val fmap2 = Map(
      1 -> 0.35,
      2 -> 0.20,
      3 -> 0.15,
      4 -> 0.15,
      5 -> 0.10,
      6 -> 0.05)

    println(HaffmanTreeBuilder(fmap2).generateCode)
  }
}
