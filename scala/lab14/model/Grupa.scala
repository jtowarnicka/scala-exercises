package jp1.akka.lab13.model

import akka.actor.{Actor, ActorRef, PoisonPill}

object Grupa {
  case object Runda
  // Zawodnicy mają wykonać swoje próby – Grupa
  // kolejno (sekwencyjnie) informuje zawodników
  // o konieczności wykonania próby i „oczekuje”
  // na ich wynik (typu Option[Ocena])
  case object Wyniki
  // Polecenie zwrócenia aktualnego rankingu Grupy
  // Oczywiście klasyfikowani są jedynie Zawodnicy,
  // którzy pomyślnie ukończyli swoją próbę
  case class Wynik(ocena: Option[Ocena])
  // Informacja o wyniku Zawodnika (wysyłana przez Zawodnika do Grupy)
  // np. Wynik(Some(Ocena(10, 15, 14)))
  // Jeśli zawodnik nie ukończy próby zwracana jest wartość Wynik(None)
  case object Koniec
  // Grupa kończy rywalizację
}
class Grupa(zawodnicy: List[ActorRef]) extends Actor {
  import Grupa._
  val organizator = context.actorSelection("akka://system/user/organizator")
  def receive: Receive = {
    case Runda => {
      for {
      zawodnik <- zawodnicy
      } zawodnik ! Zawodnik.Próba
      context.become(rywalizacja(zawodnicy))
    }
  }
  def rywalizacja(zawodnicy: List[ActorRef]): Receive = {
    case Wynik(ocena: Option[Ocena]) => {
      if (zawodnicy.size > 1) {
        organizator ! Organizator.Wyniki(Map(sender() -> ocena))
        context.become(rywalizacja(zawodnicy.tail))
      } else {
        organizator ! Organizator.Wyniki(Map(sender() -> ocena))
        organizator ! Koniec
      }
    }
  }
}
