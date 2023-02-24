def nwd(a: Int, b: Int): Int = {
    if (b!=0)
        return nwd(b, a%b)
    return a
}

@main
def lab02zad02: Unit = {
    val a = 15
    val b = 5
    println(nwd(a, b))
}