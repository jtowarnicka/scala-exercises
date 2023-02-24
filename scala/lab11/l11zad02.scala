object lab11zad02 {
    import akka.actor.*

    case object Ball
    case class Graj(przeciwnik: ActorRef, max: Int)
    
    class Gracz2 extends Actor {
        def receive: Receive = {
            case Graj(gracz, max) => 
                gracz ! Ball
                context.become(counter(max-2))
            case Ball => 
                println(s"Pileczka: ${self.path.name}")
                sender() ! Ball
                
   
        }
        def counter(i: Int): Receive = {
            case Ball => 
                if (i > 0) {
                    println(s"Pileczka: ${self.path.name}");
                    sender() ! Ball
                    context.become(counter(i-2))
                } else {
                    println(s"Pileczka: ${self.path.name}");
                    context.system.terminate()
                }
        }
    }

    // case class Ball(max: Int, counter: Int)
    // case class Graj(przeciwnik: ActorRef, max: Int)

    // class Gracz2 extends Actor {
    //     def receive: Receive = {
    //         case Graj(przeciwnik, max) => przeciwnik ! Ball(max, 1)
    //         case Ball(max, i) => 
    //             if  (i < max) 
    //                 println(s"Pileczka: ${self.path.name}");
    //                 sender() ! Ball(max, i+1)
    //             else 
    //                 println(s"Pileczka: ${self.path.name}");
    //                 context.system.terminate()
    //     }
    // }
    
    def main(args: Array[String]): Unit = {
        val system = ActorSystem("PingPong_limit")
        try {
            val gracz1 = system.actorOf(Props[Gracz2](), "gracz1")
            val gracz2 = system.actorOf(Props[Gracz2](), "gracz2")
            val max = 10
            gracz1 ! Graj(gracz2, max)
            io.StdIn.readLine()
        } 
        finally {
            system.terminate()
        }

    }
}
