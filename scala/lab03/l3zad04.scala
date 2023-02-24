import scala.annotation.tailrec

// def tasuj(l1: List[Int], l2: List[Int]): List[Int] = {
//     @annotation.tailrec
//     def helper(l1: List[Int], l2: List[Int], acc: List[Int]): List[Int] = {
//         if (l1 == Nil && l2 == Nil) return acc

//         if (!l1.isEmpty && l1.head <= l2.head)
//             if (!acc.isEmpty && l1.head == acc.last) helper(l1.tail, l2, acc)
//             else helper(l1.tail, l2, acc.appended(l1.head))
//         else
//             if (!acc.isEmpty && l2.head == acc.last) helper(l1, l2.tail, acc)
//             else helper(l1, l2.tail, acc.appended(l2.head))
//     }
//     helper(l1,l2, List())
// }

def tasuj(l1: List[Int], l2: List[Int]): List[Int] = {
    @annotation.tailrec
    def polacz(l1: List[Int], l2: List[Int], acc: List[Int] = List()): List[Int] = {
        (l1, l2) match {
            case (Nil, Nil) => acc.reverse
            case (Nil, head::tail) => polacz(l1, tail, head::acc)
            case (head::tail, Nil) => polacz(tail, l2, head::acc)
            case (head1::tail1, head2::tail2) if (head1 <= head2) => polacz(tail1, l2, head1::acc)
            case (head1::tail1, head2::tail2) if (head1 > head2) => polacz(l1, tail2, head2::acc)
            case _ => acc.reverse
        }
    }
    val stepone = polacz(l1, l2)
    def helper(l: List[Int], acc: List[Int] = List()): List[Int] = {
        l.match {
            case Nil => acc.reverse
            case head::tail => acc match {
                case Nil => helper(tail, head::acc)
                case ahead::atail if (head != ahead) => helper(tail, head::acc)
                case _ => helper(tail, acc)
            }
        }
    }
    helper(stepone)
}

@main 
def lab03zad04(): Unit = {
    println(tasuj(List(2, 4, 3, 5), List(1, 2, 2, 3, 1, 5)))
}
