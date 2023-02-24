object lab11zad04 {
    import akka.actor.*

    case object Pileczka
    case class Graj(przeciwnicy: List[ActorRef])
    
    class Gracz4 extends Actor {
        def receive: Receive = {
            case Graj(first::second::tail) => 
                context.become(zGraczem(second))
                // second ! Graj((self::(second::tail).reverse).reverse)
                second ! Graj(second::(tail:+self))    
        }
        def zGraczem(gracz: ActorRef): Receive = {
            case Graj(_) => gracz ! Pileczka
            case Pileczka => 
                println(s"Pileczka: ${self.path.name}")
                gracz ! Pileczka
        }
    }

    // case class Pileczka(przeciwnicy: List[ActorRef], n: Int)
    // case class Graj(przeciwnicy: List[ActorRef], n: Int)

    // class Gracz4 extends Actor {
    //     def receive: Receive = {
    //         case Graj(przeciwnicy, n) => przeciwnicy(n) ! Pileczka(przeciwnicy, n)
    //         case Pileczka(przeciwnicy, n) => 
    //             println(s"Piłeczka ${self.path.name}")
    //             if (n < przeciwnicy.size-1) przeciwnicy(n+1) ! Pileczka(przeciwnicy, n + 1)
    //             else przeciwnicy.head ! Pileczka(przeciwnicy, 0)
    //     }
    // }

    def main(args: Array[String]): Unit = {
        val system = ActorSystem("PingPong_4")
        try {
            val actorList = for {
                i <- (1 to 5).toList
            } yield system.actorOf(Props[Gracz4](), s"gamer${i}")
            actorList.head ! Graj(actorList)
            
            // val przeciwnicy = (1 to 5).toList
            // .map(n => system.actorOf(Props[Gracz4](), s"gracz${n}"))
            // przeciwnicy.head ! Graj(przeciwnicy, 0)

            // println("Liczba graczy: ")
            // val liczba_graczy = io.StdIn.readLine().toInt
        
        } finally {
            system.terminate()
        }
    }
}
