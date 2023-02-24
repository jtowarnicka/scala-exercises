def position[A](l: List[A], el: A): Option[Int] = {
    if (l.indexOf(el) != -1) Option(l.indexOf(el))
    else None
}

@main
def lab07zad03(): Unit = {
    val lista = List(2, 1, 1, 5)
    println(position(lista, 1))
}