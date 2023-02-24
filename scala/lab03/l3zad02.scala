import scala.annotation.tailrec

def jestPierwsza(n: Int): Boolean = {
    @annotation.tailrec
    def helper(n: Int, acc: Int): Boolean = {
        if (n == 2) return true
        if (n < 2 || n%2 == 0) return false
        if (n%acc == 0) return false
        if (acc*acc > n) return true

        helper(n, acc+2)
    }
    helper(n, 3)
}

@main
def lab03zad02: Unit = {
    println(jestPierwsza(11))
}

// def isPrime(n: Int): Boolean = {
//     @tailrec
//     def helper(t:Int, acc: Boolean): Boolean = {
//       if (!acc) false
//       else if (t<= 1) true
//       else helper(t-1, n%t != 0 && acc)
//     }
//     helper(n/2, true)
// }
// println(isPrime(17))