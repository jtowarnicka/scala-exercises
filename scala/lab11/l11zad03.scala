object lab11zad03 {
    import akka.actor.*

    case object Pileczka
    case class Graj(przeciwnik: ActorRef, kolejny: ActorRef)
    
    class Gracz3 extends Actor {
        var licznik = 10; // do nauki jak działa zmiana stanu
        def receive: Receive = {
            case Graj(przeciwnik, kolejny) => 
                println("kontekst akutalnego gracza" + context.self)
                println("poprzednik do zmiany stanu" + sender().toString)
                context.become(zGraczem(przeciwnik))
                println("następny")
                przeciwnik ! Graj(kolejny, self)
        }
        def zGraczem(gracz: ActorRef): Receive = {
            case Graj(_,_) => 
                // gracz3 ! Graj(gracz1, gracz2), gracz jeden jest w nowym stanie
                // zatem: gracz1(gracz: gracz2), stąd -> piłeczka: gracz2
                gracz ! Pileczka
            case Pileczka => 
                println("aktualnie odbija piłeczkę"+ context.self)
                println("sender piłeczki" + sender().toString)
                if (licznik > 0) {
                    println(s"Pileczka: ${self.path.name}")
                    gracz ! Pileczka
                    // gracz2(gracz3) - > piłeczka: gracz3
                    // gracz3(gracz1) - > piłeczka: gracz1...
                    licznik -= 1
                } else {
                    context.system.terminate()
                }

        }
    }

    // case class Pileczka(przeciwnik: ActorRef, kolejny: ActorRef)
    // case class Graj(przeciwnik: ActorRef, kolejny: ActorRef)

    // class Gracz3 extends Actor {
    //     def receive: Receive = {
    //         case Graj(przeciwnik, kolejny) => przeciwnik ! Pileczka(kolejny, self)
    //         case Pileczka(przeciwnik, kolejny) => 
    //             println(s"Piłeczka ${self.path.name}");
    //             przeciwnik ! Pileczka(kolejny, self);
    //     }
    // }


    def main(args: Array[String]): Unit = {
        val system = ActorSystem("PingPong")
        try {
            val gracz1 = system.actorOf(Props[Gracz3](), "gracz1")
            val gracz2 = system.actorOf(Props[Gracz3](), "gracz2")
            val gracz3 = system.actorOf(Props[Gracz3](), "gracz3")
            gracz1 ! Graj(gracz2, gracz3)
            // val actorList = for {
            //     i <- 1 to 10
            // } yield system.actorOf(Props[Gracz3](), s"g${i}")
        } finally {
            system.terminate()
        }
    }
}
