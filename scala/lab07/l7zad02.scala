def sumOpts(seq: Seq[Option[Double]]): Option[Double] = {
  if seq.forall(x => x.isEmpty) then return None
  seq.reduce((acc, element) => {
    if element.isEmpty then acc
    else Option(acc.get + element.get)
  })

}

@main
def lab07zad02(): Unit = {
    println(sumOpts(List(Some(5.4), Some(-2.0), Some(1.0), None, Some(2.6))))
}