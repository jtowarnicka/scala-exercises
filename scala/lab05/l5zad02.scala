import scala.annotation.tailrec

def skompresuj[A](l: List[A]): List[(A, Int)] = {
    @annotation.tailrec
    def helper(l: List[A], acc: List[(A, Int)] = Nil, str: String = ""): List[(A, Int)] = l match {
        case Nil => acc.reverse
        case head::tail => tail match {
            case Nil => acc match {
                case accHead::accTail if (head != accHead) => helper(tail, (head, (str+head).length)::acc)
                case _ => helper(tail, acc, str+head)
            }
            case head2::tail2 if (head != head2) => helper(tail, (head, (str+head).length)::acc)
            case _ => helper(tail, acc, str+head)
        }
    }
    helper(l)
}

@main
def lab05zad02(): Unit = {
    println(skompresuj(List[Char]('a', 'a', 'b', 'c', 'c', 'c', 'a', 'a', 'b', 'd')))
}
