import scala.annotation.tailrec

def sumuj(l: List[Option[Double]]): Option[Double] = {
    @annotation.tailrec
    def helper(l: List[Option[Double]], acc: Double = 0): Option[Double] = {
        l match {
            // List() = Nil
            case List() => Some(acc)
            // jeśli lista ma dalej elementy
            case head::tail => head match {
                // dodanie wartosci do akumulatora
                case Some(d) => 
                    if (d>0) helper(tail, head.get + acc)
                    else helper(tail, acc)
                case None => helper(tail, acc)

            }
        }
    }
    helper(l) 
}

@main
def lab04zad01(): Unit = {
    println(sumuj(List(Some(2.0), Some(4.0), Some(-3.0), None, Some(-3.0), None, Some(1.0))))
}



// Nil is equivalent to List(), you can also write that case expression like this:
    // case Nil => 0
    // case List() => 0