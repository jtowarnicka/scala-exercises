def masterMind[A](secret: List[A], guess: List[A]): (Int, Int) = {
    // val black = secret.zip(guess).filter(el => el match {case (el1, el2) => (el1 == el2)}).map((el,_) => el).length
    // val white = guess.distinct.foldLeft(0)((acc, curr) => {
    //     if (secret contains curr) {
    //         if (acc == 0) then 1
    //         else acc + 1
    //     } else {
    //         acc
    //     }
    // })
    // (black, white)

    // val secretMap = secret.groupBy(number => number)
    // val guessMap = guess.groupBy(number => number)
    // println(secretMap)
    // println(guessMap)
    // val white = guessMap.map(element => {
    //     secretMap.getOrElse(element(0), List())
    //     .length
    //     .min(element(1).length)
    // })
    // .sum - black
    // println(white)

    val trafione = secret.intersect(guess).size
    val secretZipped = secret.zipWithIndex
    val guessZipped = guess.zipWithIndex
    val czarne = secretZipped.intersect(guessZipped).size
    val biale = trafione - czarne
    
    (czarne, biale)
}

@main
def lab08zad01(): Unit = {
    val secret = List(1, 1, 1, 1, 1, 5)
    val guess = List(1, 1, 5, 1, 1, 2)
    // val secret = List(1, 3, 2, 2, 4, 5)
    // val guess = List(2, 1, 2, 4, 7, 2)
    println(masterMind(secret, guess))
    // val black = 1
    // val white = 3
}