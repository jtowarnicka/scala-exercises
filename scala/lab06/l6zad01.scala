def subSeq[A](seq: Seq[A], begIdx: Int, endIdx: Int): Seq[A] = {
    seq.take(begIdx).drop(endIdx)
    // dla czytelnosci
    // val s = seq.take(begIdx)
    // s.drop(endIdx)
}
@main
def lab06zad01(): Unit = {
    println(subSeq(Seq(11,12,13,14,15,16,14,18), 5, 2))
}