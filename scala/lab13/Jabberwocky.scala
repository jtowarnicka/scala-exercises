import akka.actor.*
import scala.concurrent.duration._
import scala.util.Random

object SiłaWyższa {
  case object Cyk
  case object Strzelać
  case class Wróg(przeciwnik: ActorRef)
}

class SiłaWyższa extends Actor {
  import SiłaWyższa._
  val zamek1 = context.actorOf(Props[Zamek](), "zamek1")
  val zamek2 = context.actorOf(Props[Zamek](), "zamek2")

  def receive: Receive = start(zamek1, zamek2)

  def start(zamek1: ActorRef, zamek2: ActorRef): Receive = {
    case Cyk =>
      println("Cyk")
      zamek1 ! Wróg(zamek2)
      zamek2 ! Wróg(zamek1)
      val zamki = context.actorSelection("/user/SilaWyzsza/zamek*")
      context.become(batalia(zamki))
  }
  def batalia(zamki: ActorSelection): Receive = {
    case Cyk => zamki ! Strzelać
  }
}

object Zamek {
  case class Strzelaj(przeciwnik: ActorRef)
}

class Zamek extends Actor {
  import Zamek._
  val listaObrońców = for {
    i <- (1 to 100)
  } yield {
   val aktor = context.actorOf(Props[Obrońca](), s"wojownik${i}")
   context.watch(aktor)
   aktor
  }
  def receive: Receive = {
    case SiłaWyższa.Wróg(przeciwnik: ActorRef) => {
      println(s"${self.path.name}: znam już przeciwnika! nienawidzę go: ${przeciwnik}")
      context.become(organizacja(listaObrońców.toList, przeciwnik))
    }
  }
  def organizacja(listaObrońców: List[ActorRef], przeciwnik: ActorRef): Receive = {
    case SiłaWyższa.Strzelać => for {
      wojownik <- listaObrońców
    } wojownik ! Strzelaj(przeciwnik)
    case Obrońca.Strzał => {
      println(s"${self.path.name}, alive: ${listaObrońców.size}")
      listaObrońców(Random.nextInt(listaObrońców.length)) ! Obrońca.STK(listaObrońców.length)
    }
    case Terminated(wojownik: ActorRef) => {
      if (listaObrońców.length == 1) {
        println(s"Przegrany: ${self.path.name}")
        context.system.terminate()
        // context.parent ! TerminateYourself
      } else {
    
        context.become(organizacja(listaObrońców.filter(actor => actor != wojownik), przeciwnik))
      }
    }
  }
}

object  Obrońca {
  case object Strzał
  case class STK(n: Int)
}

class Obrońca extends Actor {
  import Obrońca._
  def receive: Receive = {
    case Zamek.Strzelaj(przeciwnik: ActorRef) => przeciwnik ! Strzał
    case STK(n: Int) => {
      val traf = Random.nextDouble <=  n/(2.0 * 100)
      if traf then {
        // println(s"${self.path.name}: no to koniec…")
        self ! PoisonPill
      } 
      // else {
        // println(s"${self.path.name}: oberwałem, ale żyję")
      // }
    }
  }
}

@main
def bitwa: Unit = {
  val system = ActorSystem("Jabberwocky")
  import system.dispatcher

  // UWAGA: „nazwy”, tworzące ścieżkę do aktora muszą być zapisywane
  // z użyciem znaków znaków ASCII (a nie np. UTF8) – stąd „SilaWyzsza”
  val siłaWyższa = system.actorOf(Props[SiłaWyższa](), "SilaWyzsza")

  // Do „animacji” SiłyWyższej wykorzystamy „Planistę” (Scheduler)
  val pantaRhei = system.scheduler.scheduleWithFixedDelay(
    Duration.Zero,     // opóźnienie początkowe
    1000.milliseconds, // odstęp pomiedzy kolejnymi komunikatami
    siłaWyższa,        // adresat „korespondencji”
    SiłaWyższa.Cyk     // komunikat
  )

  // Oczywiście zatrzymanie symulacji NIE MOŻE się odbyć tak, jak poniżej
  // Thread.sleep(10000)
  // val res = if pantaRhei.cancel()
  //   then "Udało się zakończyć „cykanie”"
  //   else "Coś poszło nie tak – dalej „cyka”"
  // println(res)
  // system.terminate()
}
