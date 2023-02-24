def hipoteza(n: Int): Unit = {

    var wynik = s"${n} == "
    var liczby = ""
    for(j <- 2 to n) {
        for (k <- 2 to j) {
            if(pierwsza(j) == true && pierwsza(k) == true) {
                if(j+k == n) {
                    liczby = liczby ++ s"${j} + ${k} \n"
                }
            }  
        }
    }
    wynik = wynik ++ liczby
    print(wynik)
    // return wynik
}

@main
def lab02zad04: Unit = {
    val n = 20
    if (n>2 && n%2==0) {
        hipoteza(n)
        // println(s"${n} == " + hipoteza(n))
    } else {
        println("Liczba nie jest liczbą parzystą większą od 2.")
    }
}
