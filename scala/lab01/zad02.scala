def obramuj(napis: String): String = {
  val tablica = napis.split('\n')
  val maksimum = tablica.maxBy(s => s.length).length
  val linijki = tablica.map(s => "* " + s + " *").length
  val ilosc = maksimum+1
    
  var druk = ""

  for(a <- 0 to ilosc) {
    druk = druk ++ "*"
  }

  for (b <- tablica) {
    var spacje = maksimum - b.length
    if (b.length != maksimum) {
      var x = " " * spacje
      druk = druk ++ "\n" + "*" + b + x + "*"
    } else {
      druk = druk ++ "\n" + "*" + b + "*"
    }
  }
  druk = druk ++ "\n"

  for(a <- 0 to ilosc) {
    druk = druk ++ "*"
  }
  return druk
}

@main
def Ramka(): Unit = { 
println(obramuj("nosimy maski\njak u gombrowicza\nbo chcemy kochać\njak kochanowski"))
}

// println("nosimy maski\njak u gombrowicza\nbo chcemy kochać\njak kochanowski"++"aaa")
