import scala.annotation.tailrec

def reverse(str: String): String = {
    @annotation.tailrec
    def helper(str: String, acc: String): String = {
        if (str == "") acc
        // else reverse(str.tail) ++ str.head
        else helper(str.tail, str.head +:acc)
    }
    helper(str, "")
}

@main 
def lab03zad01(n: String): Unit = {
    println(reverse(n))
}
