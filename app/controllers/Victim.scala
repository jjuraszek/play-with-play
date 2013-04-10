package controllers

import play.api.data.Form
import play.api.data.Forms.{ email, mapping, number, optional, text }
import play.api.mvc.{ Action, Controller }

case class Flesh(name: String, age: Int, email: Option[String])

object Victim extends Controller {
  val form = Form(
    mapping(
      "name" -> text,
      "age" -> number(min = 1, max = 18),
      "email" -> optional(text).verifying("invalid email", field => field.isEmpty || field.get.contains("@")))(Flesh.apply)(Flesh.unapply))

  def submit = Action { implicit request =>
    form.bindFromRequest.fold(
      form => Ok(form.errors.toString),
      flesh => Ok(flesh.toString))
  }
}