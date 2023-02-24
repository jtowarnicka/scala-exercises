def indices[A](l: List[A], el: A): Set[Int] = {
    l.zipWithIndex
    .filter(a => a match { case (a,_) => a == el})
    .map(a => a._2)
    .toSet
}

@main
def lab07zad04(): Unit = {
    val lista = List(1, 2, 1, 1, 5)
    println(indices(lista, 1)) // ==> Set(0, 2, 3)
    println(indices(lista, 7)) // ==> Set()
}

