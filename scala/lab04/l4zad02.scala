import scala.annotation.tailrec

def maksimum(l1: List[Double], l2: List[Double]): List[Double] = {
    @annotation.tailrec
    def helper(l1: List[Double], l2: List[Double], acc: List[Double] = List()): List[Double] = {
        (l1, l2) match {
            case (List(), List()) => acc
            case (Nil, List(_*)) => acc ++ l2
            case (List(_*), Nil) => acc ++ l1
            // _* tells the compiler to pass each element of arr as its own argument
            case (head1::tail1, head2::tail2) =>
                if (head1>head2) helper(tail1, tail2, acc :+ head1)
                else helper(tail1, tail2, acc :+ head2)
        }
    }
    helper(l1, l2)
}

@main
def lab04zad02(): Unit = {
    println(maksimum(List(2.0, -1.6, 3.2, 5.4, -8.4), List(3.3, -3.1, 3.2, -4.1, -0.4, 5.5)))
}


// prepend (aliased as +:)
// append (aliased as :+)
// prependAll (aliased as ++:)
// concat(aliased as ++)

// appendAll (aliased as :++)
// add (aliased as +)
// addAll (aliased as ++, alias for concat)
