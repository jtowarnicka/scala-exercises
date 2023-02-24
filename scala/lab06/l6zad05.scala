def isOrdered[A](seq: Seq[A])(leq:(A, A) => Boolean): Boolean = {
    // seq.sliding(2,1).toList.foldLeft(true)((acc, elem) => {
    //     if (leq(elem(0), elem(1))) acc
    //     else false
    // })
    seq.sliding(2).map {
        case one::two::tail => leq(one,two)
        // tail lub Nil
    }.reduceLeft(_&&_)
    // seq.sliding(2, 1).toList.foldLeft[Boolean](true)(
    // (acc, el) => el match {
    //     case List(el1: A, el2: A) if acc => leq(el1, el2)
    //     case _ if !acc => acc
    //     case _ => acc
    // })
}

@main
def lab06zad05(): Unit = {
    println(isOrdered(Seq(1, 2, 2, 4))(_<_))
    println(isOrdered(Seq(1,2,2,4))(_<=_))
}
