import scala.annotation.tailrec

def isOrdered[A](l: List[A])(leq: (A, A) => Boolean): Boolean = {
    @annotation.tailrec
    def helper(l: List[A])(leq: (A, A) => Boolean): Boolean = l match {
        case Nil | List(_) => true
        case first::second::rest if leq(first, second) => helper(second::rest)(leq)
        case first::second::rest => false
    }
    helper(l)(leq)
}

// def isOrdered[A](l: List[A])(leq: (A, A) => Boolean): Boolean = {
//     @annotation.tailrec
//     def helper(l: List[A])(leq: (A, A) => Boolean): Boolean = {
//         l match {
//         case Nil => true
//         case head::tail => tail match {
//             case Nil => true
//             case head2::tail2 if leq(head, head2) => helper(head2::tail2)(leq)
//             case _ => false 
//             }
//         }
//     }
//     helper(l)(leq)
// }

@main
def lab05zad03(): Unit = {
    println(isOrdered(List(-2, 1, 3, 6))(_<_))
    println(isOrdered(List(1, 3, 4, 4))(_<=_))
    println(isOrdered(List(1))(_>_))
}

