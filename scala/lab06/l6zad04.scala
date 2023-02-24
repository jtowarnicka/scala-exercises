def remElems[A](seq: Seq[A], k: Int): Seq[A] = {
    seq.zipWithIndex.filter(n => n._2 != k).map(n => n._1)
    // lub n(1) i n(0)
}
@main
def lab06zad04(): Unit = {
    println(remElems(Seq(11,12,13,14,15,16,17), 4))
}
