// funkcja wyższego rzędu - która przyjmuje albo zwraca funkcję

// val helloStr = "Hello, World!"

type Pred[A] = A => Boolean

def and[A](p: Pred[A], q: Pred[A]): Pred[A] = {
    (a: A) => p(a) && q(a)
}

// sort[A](l: List[A])(leq: (A, A) => Boolean)

def q1(a: Int) = {
    a < 100
}

@main 
def lab5przyklad: Unit = {
    val p1 = (a: Int) => a > 2
    println(and(p1, q1)(50))
    // sort(List(5,4,7))(_>_)
    // sort(List("fff", "g", "tt"))(_.length > _.length)
}