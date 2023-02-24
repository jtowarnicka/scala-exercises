import scala.annotation.tailrec

// def oczysc[A](l: List[A]): List[A] = {
//     @annotation.tailrec
//     def helper(l: List[A], acc: List[A] = List()): List[A] = {
//         l match {
//             case Nil => acc
//             case head::tail => tail match {
//                 case head2::tail2 => 
//                     if (head == head2) helper(tail, acc) 
//                     else helper(tail, acc:+ head)
//                 case Nil => helper(tail, acc:+ head)
//             }       
//         }
//     }
//     helper(l)
// }

def oczysc[A](l: List[A]): List[A] = {
    @annotation.tailrec
    def helper(l: List[A], acc: List[A] = List()): List[A] = l match {
        case Nil => acc.reverse
        case head::tail => tail match {
            case head2::tail2 => 
                if (head == head2) helper(tail, acc) 
                else helper(tail, head::acc)
            case Nil => helper(tail, head::acc)
            }       
        }
    helper(l)
}

@main
def lab05zad01(): Unit = {
    println(oczysc(List(1, 1, 2, 4, 4, 4, 1, 3)))
}
