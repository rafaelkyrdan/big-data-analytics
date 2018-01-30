package standart

import scala.util.Try

object Util {
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
