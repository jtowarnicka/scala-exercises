def deStutter[A](seq: Seq[A]): Seq[A] = {
    // seq.foldLeft(Seq(): Seq[A])(
    // (acc, elem)  => {
    //     acc match {
    //         case Seq() => acc :+ elem
    //         case _ if acc.last == elem => acc
    //         case _ => acc :+ elem
    //     }
    // })
    
    // if (acc.indexOf(elem) == -1) acc :+ elem
    // else acc

    seq.toList.foldRight(List[A]()){ (el, acc) =>
        if (acc.isEmpty || acc.head != el) el :: acc
        else acc
    }.toSeq
}

@main
def lab06zad03(): Unit = {
    println(deStutter(Seq(1, 1, 2, 4, 4, 4, 1, 3))) // Seq(1, 2, 4, 1, 3)
}