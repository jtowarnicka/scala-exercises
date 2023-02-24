def freq[A](seq: Seq[A]): Set[(A, Int)] = {
    seq.groupBy(identity).toSet.map(a => (a._1, a._2.length))
}

@main
def lab07zad01(): Unit = {
    println(freq(Seq('a','b','a','c','c','a')))
}