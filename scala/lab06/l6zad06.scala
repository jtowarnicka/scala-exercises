def freqMax[A](list: List[A]): (Set[A],Int) = {
// def freqMax[A](list: List[A]) = {
    // list.groupMapReduce(identity)(_ => 1)(_+_).maxByOption(_._2)

    // val powtorzenia = list.map(n => (n, list.count(x => x == n))).toSet
    // val maksimum = powtorzenia.maxBy(_._2).toList(1)
    // val max = maksimum.hashCode
    // (powtorzenia.filter((_, x) => x == maksimum).map((n, _) => n), max)

    // zwraca kody
    val s = list.sortWith(_.hashCode < _.hashCode)
    .foldLeft[Seq[Seq[A]]](Seq())(
        // chcę utworzyc Seq(Seq(11)),...), sprawdzajac foldem czy element sie powtarza
        (acc, el) => acc match
            case Seq() => Seq(el)+:acc
            // przechodzę po kolejnym
            // pobieram ostatni element sekwencji jezeli jest rowny to go dodaje
            case h1::t if acc.head.head == el => (el+:h1)+:acc.drop(1) // zwracam bez pierwszego elementu
            // h1 wyłuskuje pierwszy element // doklejam pierwszy element do acc
            // Seq(2,2,2) jak bede miec znowu dwojke to bede  2::Seq(2,2,2) i usune Seq(2,2,2)

            case _ => Seq(el)+:acc
            // w sekwencjach nie ma ::
    )
    .map(a => (a.head, a.length))
    val maxS = s.maxBy(a => a match { case (_, l) => l})
    // maxby zwraca najwiekszy wzgledem tej funkcji jaka podaje
    val len = maxS match { case (_, l) => l}
    val s2 = s.filter(a => a match { case (_, l) => l == len })
    .map(a => a match { case (el, _) => el})
    .toSet

    (s2, len)
}

val l = List(1, 1, 2, 4, 4, 3, 4, 1, 3)
@main
def lab06zad06(): Unit = {
    println(freqMax(l))
}