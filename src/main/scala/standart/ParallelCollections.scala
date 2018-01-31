package standart

object ParallelCollections {

  def main(args: Array[String]): Unit = {

    val a = Array(4,4,4,7,3,1,3,4,1,3,2)

    // different results
    println(a.foldLeft(0)(_ - _))
    println(a.foldRight(0)(_ - _))


    val b = Array(4,4,4,7,3,1,3,4,1,3,2).par
    // 1st sequential function
    // 2nd combine function
    println(b.aggregate(0)(_ + _, _ + _))
  }
}
