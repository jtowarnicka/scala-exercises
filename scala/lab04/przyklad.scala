import scala.annotation.tailrec

// List(1,2,3) => 1::List(2,3)

def reverse[A](str: List[A]): List[A] = {
    @annotation.tailrec
    def helper(str: List[A], acc: List[A]): List[A] = {
        str match {
            case List() => acc
            case lHead::lTail => helper(lTail, lHead+:acc) 
        }
    }
    helper(str, List())
}

@main
def lab04zad00(): Unit = {
    println(reverse(List(1,2,3)))
}