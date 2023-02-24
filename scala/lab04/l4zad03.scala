import scala.annotation.tailrec

def usun[A](l: List[A], el: A): List[A] = {
    @annotation.tailrec
    def helper[A](l: List[A], el: A, acc: List[A] = List()): List[A] = l match {
        case Nil => acc
        case head::tail =>
        if (head == el) helper(tail, el, acc)
        else helper(tail, el, acc :+ head)
    }
    helper(l, el)
}

@main
def lab04zad03(): Unit = {
    println(usun(List(2, 1, 4, 1, 3, 3, 1, 2), 1))
}
