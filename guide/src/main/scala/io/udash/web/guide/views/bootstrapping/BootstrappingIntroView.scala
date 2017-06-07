package io.udash.web.guide.views.bootstrapping

import io.udash._
import io.udash.web.commons.views.{ClickableImageFactory, ImageFactoryPrefixSet}
import io.udash.web.guide.{Context, _}
import io.udash.web.guide.styles.partials.GuideStyles
import org.scalajs.dom

import scalatags.JsDom

case object BootstrappingIntroViewPresenter extends DefaultViewPresenterFactory[BootstrappingIntroState.type](() => new BootstrappingIntroView)

class BootstrappingIntroView extends FinalView {
  import Context._

  import JsDom.all._
  import scalacss.ProdDefaults._
  import scalacss.ScalatagsCss._

  override def getTemplate: Modifier = div(
    h2("Project structure"),
    p("The basic Udash project contains three modules: "),
    ClickableImageFactory(ImageFactoryPrefixSet.Boostrapping, "modules_basic.png", "Basic modules structure.", GuideStyles.get.floatRight, GuideStyles.get.imgSmall, GuideStyles.get.imgIntro),
    ul(GuideStyles.get.defaultList)(
      li(
        "shared - contains ", a(href := RpcIntroState.url)("RPC"), " interfaces, shared model and logic (e.g model validation). ",
        "It is cross-compiled into JavaScript and JVM bytecode, so you can use it in the frontend and backend code. ",
        "It is useful when you want to use data models both in frontend and backend. Udash uses this module to share RPC interfaces."
      ),
      li(
        "backend - contains ", a(href := RpcIntroState.url)("RPC"), " and the whole backend application logic. ",
        "It is compiled only to bytecode. You can write your backend logic like database access, services layer etc. there."
      ),
      li(
        "frontend - contains frontend application code. It is compiled only to JS and packed into three files: ",
        i("frontend-deps.js"), ", ", i("frontend-impl.js"), " and ", i("frontend-init.js"), ""
      )
    ),
    p(
      "You can split the backend module into a more complex structure to keep a code cleaner - it is all up to you. This guide " +
      "keeps the basic structure with three modules."
    ),
    ClickableImageFactory(ImageFactoryPrefixSet.Boostrapping, "modules_extended.png", "More complex modules structure.", GuideStyles.get.imgBig, GuideStyles.get.frame),
    h2("What's next?"),
    p(
      "If you want to prepare a custom project, you might be interested in ",
      a(href := BootstrappingSBTState.url)("SBT configuration"), " "
    )
  )
}