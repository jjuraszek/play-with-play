import play.api._
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

object Global extends WithFilters(TimeFilter) with GlobalSettings {

}

object TimeFilter extends Filter {
  def apply(next: (RequestHeader) => Result)(rh: RequestHeader) = {
    val start = System.currentTimeMillis

    def logTime(result: PlainResult): Result = {
      val time = System.currentTimeMillis - start
      if (result.header.status == 200) {
        Logger.info(s"${rh.uri} time consumption: ${time}ms")
      }
      result.withHeaders("Request-Time" -> time.toString)
    }

    next(rh) match {
      case plain: PlainResult => logTime(plain)
      case async: AsyncResult => async.transform(logTime)
    }
  }
}