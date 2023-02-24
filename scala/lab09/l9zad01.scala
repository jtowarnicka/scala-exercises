def findOsoby(list: List[String]) = {
  list.map(n => n.split(" ", 2).toList)
  .groupBy(n => n(0).split("").toList.distinct.length)
  .maxBy(n => n(0))._2
  .groupBy(n => n(1).length)
  .minBy(n => n(0))._2
  .map(l => (l.head, l.tail.head))
}
@main
def lab09zad01: Unit = {
 val linie = io.Source
  .fromResource("nazwiska.txt")
  .getLines.toList
  println(findOsoby(linie))
}