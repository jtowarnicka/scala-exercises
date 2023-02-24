import scala.annotation.tailrec

def divide[A](l: List[A]): (List[A], List[A]) = {
    @annotation.tailrec
    def helper[A](l: List[A], acc: (List[A], List[A]) = (Nil, Nil), index: Int = 0): (List[A], List[A]) = {
        l match {
            case Nil => acc
            case head::tail =>
            if (index%2==0) helper(tail, (acc(0) :+ head, acc(1)), index + 1)
            else helper(tail, (acc(0), acc(1) :+ head), index + 1)
        }
    }
    helper(l)
}

@main
def lab04zad04(): Unit = {
    println(divide(List(1, 3, 5, 6, 7)))
}