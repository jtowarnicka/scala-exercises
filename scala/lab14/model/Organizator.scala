package jp1.akka.lab13.model

import akka.actor.{Actor, ActorRef, Props, PoisonPill}
import java.{util => ju}

val akkaPathAllowedChars = ('a' to 'z').toSet union
  ('A' to 'Z').toSet union
  "-_.*$+:@&=,!~';.)".toSet

object Organizator {
  case object Start
  // rozpoczynamy zawody – losujemy 50 osób, tworzymy z nich zawodników
  // i grupę eliminacyjną
  case object Runda
  // polecenie rozegrania rundy (kwalifikacyjnej bądź finałowej) –  wysyłamy Grupa.Runda
  // do aktualnej grupy
  case object Wyniki
  // polecenie wyświetlenia klasyfikacji dla aktualnej grupy
  case class Wyniki(w: Map[ActorRef, Option[Ocena]])
  // wyniki zwracane przez Grupę
  case object Stop
  // kończymy działanie
}

class Organizator extends Actor {
  // importujemy komunikaty na które ma reagować Organizator
  import Organizator._

  def receive: Receive = {
    case Start =>
      // tworzenie 50. osób, odpowiadających im Zawodników
      // oraz Grupy eliminacyjnej
      val zawodnicy = List.fill(50) {
        val o = Utl.osoba()
        context.actorOf(Props(Zawodnik(o)), s"${o.imie}-${o.nazwisko}" filter akkaPathAllowedChars)
      }
      val grupa1 = context.actorOf(Props(Grupa(zawodnicy.toList)), "grupa_eliminacyjna")
      context.become(rozpoczęcie(grupa1))
      // println(s"${grupa1}")
    // Obsługa pozostałych komunikatów
    case Stop =>
      println("kończymy zawody...")
      context.system.terminate()
  }
  def rozpoczęcie(grupa: ActorRef): Receive = {
    case Runda => grupa ! Grupa.Runda
    context.become(wToku(grupa, Map()))
  }
  def wToku(grupa: ActorRef, wyniki: Map[ActorRef, Option[Ocena]]): Receive = {
    case Wyniki(w: Map[ActorRef, Option[Ocena]]) => {
      context.become(wToku(grupa, wyniki ++ w))
    }
    case Grupa.Koniec => {
      sender() ! PoisonPill
    }
    case Wyniki => {
      val results = wyniki.filter(n => n._2 != None).toList.sortWith((a, b) => (a, b) match {
        case ((aktor1, Some(Ocena(nota1_1, nota2_1, nota3_1))), (aktor2, Some(Ocena(nota1_2, nota2_2, nota3_2)))) => {
          val suma1 = nota1_1 + nota2_1 + nota3_1
          val suma2 = nota1_2 + nota2_2 + nota3_2
          if (suma1 > suma2) true
          else if ((suma1 == suma2) && (nota1_1 > nota1_2)) true
          else if ((suma1 == suma2) && (nota1_1 == nota1_2) && (nota2_1 > nota2_2)) true
          else if ((suma1 == suma2) && (nota1_1 == nota1_2) && (nota2_1 == nota2_2) && (nota3_1 > nota3_2)) true
          else false 
      }}).take(20)
      println("20 najlepszych zawodników zostało wyłonionych")

      val res = results.map(n => n match {
        case (zawodnik, Some(Ocena(nota1, nota2, nota3))) => (zawodnik.path.name, Ocena(nota1, nota2, nota3))
      }).map(n => (n._1, n._2, n._2._1+ n._2._2+ n._2._3))
      val druk = res.foldLeft(List[(String, Ocena, Int, Int)]())((acc, el) => {
        if (acc.isEmpty) {
          acc :+ (el(0), el(1), 1, 1)
        } else {
        val poprzedni = acc.last
        (poprzedni(1), el(1)) match {
          case (Ocena(nota11, nota21, nota31), Ocena(nota12, nota22, nota32)) => {
            if (nota11 == nota12 && nota21 == nota22 && nota31 == nota32) {
              acc :+ (el(0), el(1), poprzedni(2), poprzedni(3) + 1)
            } else {
                acc :+ (el(0), el(1), poprzedni(2) + poprzedni(3), 1)
            }
          }
        }}
      }).map(n => n match { case (s"${imie}-${nazwisko}", Ocena(nota1, nota2, nota3), miejsce, _) => s"${miejsce}. ${imie} ${nazwisko} - ${nota1}-${nota2}-${nota3} = ${nota1 + nota2 + nota3}"})
      println("Ranking: ")
      druk.foreach(n => println(n))
      val zawodnicy = results.map(n => n._1).toList
      val grupa2 = context.actorOf(Props(Grupa(zawodnicy)), "grupa_finalowa")
      context.become(grandeFinale(grupa2, results.toMap))
    }
    case Stop =>
      println("kończymy zawody...")
      context.system.terminate()
  }
  def grandeFinale(grupa: ActorRef, wyniki1: Map[ActorRef, Option[Ocena]]): Receive = {
    case Runda => grupa ! Grupa.Runda
      context.become(wToku2(grupa, Map(), wyniki1))
  }
  def wToku2(grupa: ActorRef, wyniki: Map[ActorRef, Option[Ocena]], wyniki1: Map[ActorRef, Option[Ocena]]): Receive = {
    case Wyniki(w: Map[ActorRef, Option[Ocena]]) => {
      context.become(wToku2(grupa, wyniki ++ w, wyniki1))
    }
    case Grupa.Koniec => {
      sender() ! PoisonPill
    }
    case Wyniki => {
      val results2 = wyniki1.map(n => n match {
        case (zawodnik, Some(Ocena(nota1, nota2, nota3))) => {
          if (wyniki.contains(zawodnik)) {
            val ocena_ostateczna = wyniki(zawodnik).getOrElse(Ocena(0,0,0))
            ocena_ostateczna match {
              case Ocena(nota4, nota5, nota6) => (zawodnik.path.name, Ocena(nota1+nota4, nota2+nota5, nota3+nota6))
            }
          } else (zawodnik.path.name, Ocena(nota1, nota2, nota3))
        }
      })
      val ostateczne_posortowane = (results2.toList.sortWith((a, b) => (a, b) match {
        case ((aktor1, Ocena(nota1_1, nota2_1, nota3_1)), (aktor2, Ocena(nota1_2, nota2_2, nota3_2))) => {
          val suma1 = nota1_1 + nota2_1 + nota3_1
          val suma2 = nota1_2 + nota2_2 + nota3_2
          if (suma1 > suma2) true
          else if ((suma1 == suma2) && (nota1_1 > nota1_2)) true
          else if ((suma1 == suma2) && (nota1_1 == nota1_2) && (nota2_1 > nota2_2)) true
          else if ((suma1 == suma2) && (nota1_1 == nota1_2) && (nota2_1 == nota2_2) && (nota3_1 > nota3_2)) true
          else false 
      }}))
      val ranking = ostateczne_posortowane.foldLeft(List[(String, Ocena, Int, Int)]())((acc, elem) => {
        if (acc.isEmpty) {
          acc :+ (elem(0), elem(1), 1, 1)
        } else {
          val poprzedni = acc.last
          (poprzedni(1), elem(1)) match {
            case (Ocena(nota11, nota21, nota31), Ocena(nota12, nota22, nota32)) => {
              if (nota11 == nota12 && nota21 == nota22 && nota31 == nota32) {
                acc :+ (elem(0), elem(1), poprzedni(2), poprzedni(3) + 1)
              } else {
                acc :+ (elem(0), elem(1), poprzedni(2) + poprzedni(3), 1)
              }
            }
          }
        }
      }).map(n => n match { case (s"${imie}-${nazwisko}", Ocena(nota1, nota2, nota3), miejsce, _) => s"${miejsce}. ${imie} ${nazwisko} - ${nota1}-${nota2}-${nota3} = ${nota1 + nota2 + nota3}"})
      println("Ranking: ")
      ranking.foreach(n => println(n))
    }
    case Stop =>
      println("kończymy zawody...")
      context.system.terminate()
  }
}
