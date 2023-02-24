def pairPosNeg(seq: Seq[Double]): (Seq[Double], Seq[Double]) = {
    seq.filter( _ != 0.0).partition(n => n < 0.0)
}

@main
def lab06zad02(): Unit = {
    println(pairPosNeg(Seq(-11.0, -12.0, 13.0, -14.0, 15.0, -16.0, 17.0, 8.0, 10.0)))
}