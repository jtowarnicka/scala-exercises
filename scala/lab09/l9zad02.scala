@main
def lab09zad02: Unit = {
    val maks = 150
    val linie = io.Source
    .fromResource("ogniem-i-mieczem.txt")
    .getLines.toList

    def histogram(plik: List[String], maks: Int): String = {
        val prepare = linie.mkString
        .replaceAll("[^A-Za-zĄąĆćĘęŁłŃńÓóŹźŻż]+", "").toLowerCase
        .toList
        .groupBy(identity)
        .toSeq.sortBy(_._1)
        val litery = prepare.map(n => (n(0)))
        val ilosc = prepare.map(n => n(1).mkString.replaceAll("[A-Za-zĄąĆćĘęŁłŃńÓóŹźŻż]", "*"))
        .map({case n => if (n.length > maks) then (n.slice(0,maks)) else n})
        val nowe = litery.zip(ilosc).map(n => s"${n._1}: ${n._2}")
        nowe mkString "\n"
    }
    println(histogram(linie, maks))
}
