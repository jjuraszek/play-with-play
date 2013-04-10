package controllers

import play.api._

import play.api.mvc._

object Application extends Controller {
  def LoggingInterceptor(f: Request[AnyContent] => Result): Action[AnyContent] = {
    Action {request =>
      Logger.info(s"action call: ${request.path}")
      f(request)
    }
  }
  
  def index = LoggingInterceptor { request =>
    val camp=request.session.get("camp").getOrElse("<none>")
    Ok(views.html.index(s"Your new application is ready. Campaign: ${camp}"))
  }
  
  def saySomething = LoggingInterceptor { request =>
    Ok("lore ipsum")
  }
}