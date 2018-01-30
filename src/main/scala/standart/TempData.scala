package standart

import scala.util.Try

case class TempData(day: Int, doy: Int, month: Int, year: Int, precip: Double, snow: Double, tave: Double, tmax: Double, tmin: Double)

object TempData {
  def main(args: Array[String]): Unit = {
    val source = scala.io.Source.fromFile("MN212142_9392.csv")
    val lines = source.getLines().drop(1)
    val data = lines
      .flatMap { line =>
        val p = line.split(",")
        if (p(6) == "." || p(7) == "." || p(8) == ".") {
          Seq.empty[TempData]
        } else {
          Seq(
            TempData(p(0).toInt, p(1).toInt, p(2).toInt, p(4).toInt,
              toDoubleOrNeg(p(5)), toDoubleOrNeg(p(6)), p(7).toDouble, p(8).toDouble, p(9).toDouble)
          )
        }
      }.toArray

    source.close()

    // check
    data.take(5).foreach(println)

    // 1 approach
    val maxTemp = data.map(_.tmax).max
    val hotDays = data.filter(_.tmax == maxTemp)
    hotDays.foreach(println)

    // 2 approach
    val hotDay = data.maxBy(_.tmax)
    println(s"hot day: $hotDay")

    // 3 approach
    val hotDay2 = data.reduce( (b, a) => {
      if (b.tmax >= a.tmax) {
        b
      } else {
        a
      }
    })

    println(s"hot day2: $hotDay2")
  }

  def parseInt(s: String): Option[Int] = {
    Try { s.toInt }.toOption
  }

  def parseDouble(s: String): Option[Double] = {
    Try { s.toDouble }.toOption
  }

  def toDoubleOrNeg(s: String): Double = {
    parseDouble(s).getOrElse(-1)
  }
}
