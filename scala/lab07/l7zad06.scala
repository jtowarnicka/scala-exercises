@main
def lab07zad06(): Unit = {
    val strefy: Seq[String] = java.util.TimeZone.getAvailableIDs.toSeq
    .filter(timeZone => timeZone.slice(0, 6) == "Europe")
    .sortBy( n => (n.stripPrefix("Europe/").length, n.stripPrefix("Europe/")))
    // .sortWith(_.stripPrefix("Europe/").length < _.stripPrefix("Europe/").length) // tylko długości
    println(strefy)
}