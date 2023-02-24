object laboratorium12 {
  import akka.actor._
  
  case class Init(liczba: Int)
  case class Zlecenie(fragment: List[String])
  case class Wykonaj(wers: String)
  case class Wynik(n: Int)
  case object Start

  class Szef extends Actor {
    def receive: Receive = {
      case Init(liczba: Int) => {
        val listaPracowników = for {
          i <- (1 to liczba).toList
        } yield context.actorOf(Props[Pracownicy](), s"pracownik${i}")
        context.become(organizacja(listaPracowników))
      }
    }
    def organizacja(listaPracowników: List[ActorRef]): Receive = {
      case Zlecenie(fragment: List[String]) => {
        context.become(zlecenia(listaPracowników, fragment, 0))
        self ! Start
      }
    }

    def zlecenia(listaPracowników: List[ActorRef], fragment: List[String], wynik: Int): Receive = {
      case Start => {
        (fragment, listaPracowników) match {
          case (wers::frag, pracownik::pracownicy) => {
            pracownik ! Wykonaj(wers)
            context.become(zlecenia(pracownicy:+pracownik, frag, wynik))
          }
          case _ => println("NIE DZIAŁA") 
        }
      }
      case Wynik(x: Int) if (fragment.length > 0) => {
        (fragment, listaPracowników) match {
          case (wers::frag, pracownik::pracownicy) => {
            pracownik ! Wykonaj(wers)
            context.become(zlecenia(pracownicy:+pracownik, frag, wynik+x))
          }
          case _ => println("NIE DZIAŁA")
        }
      }
      case Wynik(x: Int) if (fragment.length == 0) => {
        println(wynik+x)
        context.system.terminate()
      }
    }
  }

  class Pracownicy extends Actor {
    def receive: Receive = {
      case Wykonaj(wers: String) => {
        wers match {
          case wers if (wers.length > 0) => sender() ! Wynik(wers.split(" ").toList.length)
          case _ => sender() ! Wynik(0)
        }
        
      }
    }
  }

  def main(args: Array[String]): Unit = {
    def dane(): List[String] = {
      scala.io.Source.fromResource("ogniem_i_mieczem.txt").getLines.toList
    }
    val system = ActorSystem("WordCounter")
    val szef = system.actorOf(Props[Szef](), "Szef")
    szef ! Init(4)
    szef ! Zlecenie(dane())

    // println(dane())
  }
}
