package standart

import standart.Util._

import scalafx.application.JFXApp
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.chart.{NumberAxis, ScatterChart, XYChart}
import swiftvis2.plotting
import swiftvis2.plotting.{ColorGradient, Plot}
import swiftvis2.plotting.renderer.FXRenderer

object PlotTemps extends JFXApp {
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

  // plotting with scalaFX, doesn't work good
  /*
  stage = new JFXApp.PrimaryStage {
    title = "Temp Plot"
    scene = new Scene(500, 500) {
      val xAxis = NumberAxis()
      val yAxix = NumberAxis()
      val pData = XYChart.Series[Number, Number]("Temps",
        ObservableBuffer(data.map(td => XYChart.Data[Number, Number](td.doy, td.tmax)):_*)
      )
      val plot  = new ScatterChart(xAxis, yAxix, ObservableBuffer(pData))
      root = plot
    }
  }
  */

  val rainData = data.filter(_.precip >= 0.0).sortBy(_.precip)
  val cg = ColorGradient((0.0, 0xFF000000), (1.0, 0xFF00FF00), (10.0, 0xFF0000FF))
  val plot = Plot.scatterPlot(
    rainData.map(_.doy),
    rainData.map(_.tmax),
    "Temps",
    "Day of Year",
    "Temp",
    5,
    rainData.map(td => cg(td.precip))
  )

  FXRenderer(plot, 500, 500)

}
