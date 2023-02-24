import scala.annotation.tailrec

def applyForAll[A, B](l: List[A])(f: A => B): List[B] = {
    @annotation.tailrec
    def helper(l: List[A], acc: List[B] = Nil)(f: A => B): List[B] = l match {
        case Nil => acc.reverse
        case head::tail => helper(tail, f(head)::acc)(f)
    }
    helper(l)(f)
}

@main def lab05zad04(): Unit = {
    println(applyForAll(List(1, 3, 5))(n => n + 3))
}