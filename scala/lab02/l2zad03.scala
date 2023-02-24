def pierwsza(n: Int): Boolean = {    
    if (n==2) return true
    if (n < 2 || n%2==0) return false
 
    var i = 3
    while (i*i <= n) {
        if (n%i == 0) {
            return false
        }
        i = i + 2
    }
    return true
}

@main
def lab02zad03: Unit = {
    val n = 3
    println(pierwsza(n))
}