package io.udash.web.homepage.views

import io.udash._
import io.udash.web.commons.components.Footer
import io.udash.web.commons.styles.{GlobalStyles, StyleRegistry}
import io.udash.web.commons.styles.components.FooterStyles
import io.udash.web.homepage.RootState
import io.udash.web.homepage.components.Header
import io.udash.web.homepage.styles.HomepageDefaultStyles
import io.udash.web.homepage.styles.partials._
import org.scalajs.dom.Element
import org.scalajs.dom.raw.HTMLStyleElement

import scala.scalajs.js
import scalatags.JsDom.TypedTag
import scalatags.JsDom.tags2._

object RootViewPresenter extends DefaultViewPresenterFactory[RootState.type](() => new RootView)

class RootView extends View {
  import scalacss.ProdDefaults._
  import scalacss.ScalatagsCss._
  import scalatags.JsDom.all._

  private var child: Element = div().render
  private val defaultStyles = HomepageDefaultStyles.get

  private val content = div(
    StyleRegistry.styleSheet,
    Header.getTemplate,
    main(GlobalStyles.get.main)(
      child
    ),
    Footer.getTemplate
  )

  override def getTemplate: Modifier = content

  override def renderChild(view: View): Unit = {
    import io.udash.wrappers.jquery._
    val newChild = view.getTemplate
    newChild.applyTo(child)

    js.Dynamic.global.svg4everybody()
  }
}