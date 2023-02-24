def parzysta(n: Int): Boolean = {
    if (n % 2 == 0) {
        return true
    } else {
        return false
    }
}

@main def lab02zad01(n: Int): Unit = {
    println(parzysta(n))
}
