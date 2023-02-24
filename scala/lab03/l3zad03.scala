import scala.annotation.tailrec

def ciag(n: Int): Int = {
    @annotation.tailrec
    def helper(n: Int, acc1: Int, acc2: Int): Int = {
        if (n <= 0) acc2
        else helper(n-1, acc1+acc2, acc1)
    }
    helper(n, 1, 2)
}

@main
def lab03zad03: Unit = {
    println(ciag(4))
}