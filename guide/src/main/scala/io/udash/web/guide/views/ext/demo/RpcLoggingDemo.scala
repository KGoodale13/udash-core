package io.udash.web.guide.views.ext.demo

import io.udash._
import io.udash.web.guide.demos.activity.Call
import io.udash.web.guide.styles.BootstrapStyles
import io.udash.web.guide.styles.partials.GuideStyles
import org.scalajs.dom
import org.scalajs.dom._

import scalatags.JsDom.all._

object RpcLoggingDemo {
  import scalacss.ScalatagsCss._
  import io.udash.web.guide.Context._
  def apply(model: ReadableSeqProperty[Call], loadCalls: () => Any): dom.Element =
    span(GuideStyles.frame)(
      button(id := "call-logging-demo", BootstrapStyles.btn, BootstrapStyles.btnPrimary)
      (onclick :+= ((_: MouseEvent) => {
        loadCalls()
        true
      }))("Load call list"),
      produce(model)(seq =>
        ul(
          seq.map(call => li(call.toString)): _*
        ).render
      )
    ).render
}
