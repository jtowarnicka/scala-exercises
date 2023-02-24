def swap[A](l: List[A]): List[A] = {
    // val parzyste = l.zipWithIndex.filter(n => n match { case (_,n) => n%2==0}).map(a => a._1)
    // val nieparzyste = l.zipWithIndex.collect {case (x,i) if i % 2 != 0 => x}

    l.sliding(2,2).toList.map(n => n match { case n => n.reverse }).flatten

    // @annotation.tailrec
    // def helper(l: List[A], acc: List[A] = List(), idx: Int = 0): List[A] = l match {
    //     case List() => acc.reverse
    //     case first::second::tail if idx % 2 == 0 => helper(first::tail, second::acc, idx+1)
    //     case first::second::tail if idx % 2 != 0 => helper(second::tail, first::acc, idx+1)
    //     case first::Nil => helper(Nil, first::acc)
    //     case _ => acc
    // }
    // helper(l)
}

@main
def lab07zad05(): Unit = {
    val lista = List(1, 2, 3, 4, 5)
    println(swap(lista)) // ==> List(2, 1, 4, 3, 5)
}


