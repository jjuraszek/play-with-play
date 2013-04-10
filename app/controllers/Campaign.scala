package controllers

import play.api._

import play.api.mvc._

object Campaign extends Controller {
  
  def camp(id:String) = Action {
    Logger.info(s"Campaign ${id}")
    Redirect(routes.Application.index).withSession("camp" -> id)
  }
}