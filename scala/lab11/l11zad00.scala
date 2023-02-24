// object lab11zad00 {
//     import akka.actor.*

//     // żeby program główny wiedział co to jest Pileczka i Graj
//     // zdefiniowanie wiadomości
//     case object Pileczka
//     case class Graj(gracz: ActorRef)

//     class PingActor extends Actor {
//         def receive: Receive = {
//             case Graj(gracz) => gracz ! Pileczka // wartość która mówi z kim mamy grać
//             case Pileczka => 
//                 println(s"Pileczka: ${self.path.name}")  // jeśli dstaniemy piłke
//                 sender() ! Pileczka // dowiadujemy się od kogo przyszła wiadomość - sender()
//                 // pileczka obiektem
//         }
//     }

//     // drugi rodzaj aktora
//     class PongActor extends Actor {
//         def receive: Receive = {
//             // nie musi dbać o to żeby rozpocząć gry
//             case Pileczka => println(s"Pileczka: ${self.path.name}"); sender() ! Pileczka
//             // jeśli dstaniemy piłke ! Pileczka
//         }
//     }    

//     def main(args: Array[String]): Unit = {
//         // tworzenie systemu aktorów
//         val system = ActorSystem("PingPong")
//         try {
//             val gracz1 = system.actorOf(Props[PingActor](), "gracz1")
//             val gracz2 = system.actorOf(Props[PongActor](), "gracz2")
//             gracz1 ! Graj(gracz2)
//             io.StdIn.readLine()
//         } finally {
//             system.terminate()
//         }
//     }
// }