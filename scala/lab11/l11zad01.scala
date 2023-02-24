object lab11zad01 {
    import akka.actor.*

    case object Pileczka
    case class Graj(przeciwnik: ActorRef)

    class Gracz extends Actor {
        def receive: Receive = {
            case Graj(gracz2) => gracz2 ! Pileczka
            case Graj(gracz1) => gracz1 ! Pileczka
            case Pileczka => 
                println(s"Pileczka: ${self.path.name}");
                sender() ! Pileczka
        }
    }

    def main(args: Array[String]): Unit = {
        val system = ActorSystem("PingPong")
        try {
            val gracz1 = system.actorOf(Props[Gracz](), "gracz1")
            val gracz2 = system.actorOf(Props[Gracz](), "gracz2")
            gracz1 ! Graj(gracz2)
            io.StdIn.readLine()
        } finally {
            system.terminate()
        }
    }
}
