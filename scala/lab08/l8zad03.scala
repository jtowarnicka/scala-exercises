def threeNumbers(n: Int): Seq[(Int, Int, Int)] = {
    val kombinacje = for {
        a <- (0 to n).toList
        b <- (0 to n).toList if (a < b)
        c <- (0 to n).toList if (a*a + b*b == c*c)
    } yield (a, b, c)
    kombinacje.toSeq
}

@main
def lab08zad03(): Unit = {
    println(threeNumbers(10))
}