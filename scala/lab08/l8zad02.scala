class Sportowiec(val imie: String, val nazwisko: String, val wdziek: List[Int], val spryt: List[Int]) {
    def sredniaWdzieku: Double = {
        wdziek.sum / wdziek.length
    }

    def sredniaSprytu: Double = {
        spryt.sum / spryt.length
    }
}

def wyniki(zawodnicy: List[Sportowiec]): List[(Int, String, String, Double)] = {
    
    zawodnicy
    // .sortBy(zawodnik => ((zawodnik.sredniaWdzieku + zawodnik.sredniaSprytu), zawodnik.sredniaWdzieku, zawodnik.nazwisko))
    // .reverse
    .sortWith((jeden, dwa) => {
        if ((jeden.sredniaWdzieku+jeden.sredniaSprytu) > (dwa.sredniaWdzieku + dwa.sredniaSprytu)) true
        else if ((jeden.sredniaWdzieku+jeden.sredniaSprytu) == (dwa.sredniaWdzieku + dwa.sredniaSprytu)) {
            if (jeden.sredniaWdzieku > dwa.sredniaWdzieku) true
            else if (jeden.sredniaWdzieku == dwa.sredniaWdzieku) {
                jeden.nazwisko < dwa.nazwisko
            }
            else false
        }
        else false
    })
    .zip( 1 until zawodnicy.length + 1).map((el, idx) => (idx, el.imie, el.nazwisko, (el.sredniaSprytu+el.sredniaWdzieku)))
}

@main
def lab08zad02(): Unit = {
    val zawodnik1 = new Sportowiec("Drew", "Barrymore", List(10,10,10,10,10,10), List(10,9,10))
    val zawodnik2 = new Sportowiec("Timothee", "Chalamet", List(10,10,10,10,10,10), List(10,9,10))
    val zawodnik3 = new Sportowiec("Adele", "Adkins", List(10,6,10,10), List(8,8,8))
    val zawodnik4 = new Sportowiec("Natalie", "Golmount", List(8,8,8), List(10,6,10,10))
    val zawodnik5 = new Sportowiec("Sylvia", "Gall", List(8,8,8), List(10,6,10,10))
    val zawodnik6 = new Sportowiec("Totoro", "Ghibli", List(10,6,10,10), List(8,8,8))

    val listaZawodnikow: List[Sportowiec] = List(zawodnik1, zawodnik2, zawodnik3, zawodnik4, zawodnik5, zawodnik6)
    wyniki(listaZawodnikow).foreach((el) => println(el))
    println(wyniki(listaZawodnikow))
}
