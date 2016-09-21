package io.udash.web.homepage.styles.partials

import io.udash.web.commons.styles.attributes.Attributes
import io.udash.web.commons.styles.{GlobalStyles, UdashStylesheet}
import io.udash.web.commons.styles.utils.{MediaQueries, StyleConstants, StyleUtils, UdashFonts}
import io.udash.web.homepage.components.demo.DemoComponent

import scala.language.postfixOps
import scalacss.internal.Compose
import scalacss.Defaults._

object DemoStyles extends UdashStylesheet {
  import dsl._

  val demoComponent = style(
    UdashFonts.acumin(),
    position.relative,
    height(682 px),
    marginBottom(5 rem),
    fontSize(1.4 rem),

    MediaQueries.tabletLandscape(
      style(
        height.auto
      )
    ),

    unsafeChild("input")(
      UdashFonts.acumin()
    )
  )

  val laptopImage = style(
    position.absolute,
    top(`0`),
    left(50 %%),
    maxWidth.none,
    transform := "translateX(-50%)",

    MediaQueries.tabletLandscape(
      style(
        display.none
      )
    )
  )

  val demoBody = style(
    width(990 px),
    height(600 px),
    paddingTop(85 px),
    margin(`0`, auto),

    MediaQueries.tabletLandscape(
      style(
        width.auto,
        height.auto,
        paddingTop(0 px),
        backgroundColor.black
      )
    )
  )

  val demoSources = style(
    GlobalStyles.get.col,
    width(50 %%),

    MediaQueries.tabletLandscape(
      style(
        width(100 %%)
      )
    ),

    unsafeChild("::selection")(
      backgroundColor(StyleConstants.Colors.GreyExtra)
    ),

    unsafeChild("::-moz-selection")(
      backgroundColor(StyleConstants.Colors.GreyExtra)
    )
  )

  val demoPreview = style(
    GlobalStyles.get.col,
    width(50 %%),
    padding(40 px, 50 px),

    MediaQueries.tabletLandscape(
      style(
        width(100 %%),
        padding(20 px, 30 px)
      )
    )
  )

  val demoTabs = style(
    position.relative,
    paddingTop(15 px),
    textAlign.left,
    borderColor(StyleConstants.Colors.Grey),
    borderWidth(1 px),
    borderBottomStyle.solid,

    MediaQueries.phone(
      style(
        border.none
      )
    )
  )

  val demoTabsItem = style(
    GlobalStyles.get.col,

    MediaQueries.phone(
      style(
        display.block,
        margin(`0`),
        textAlign.center
      )
    )
  )

  val demoTabsLink = style(
    position.relative,
    StyleUtils.transition(),
    display.block,
    padding(.5 rem, .9 rem),
    color(StyleConstants.Colors.Grey),
    zIndex(2),

    &.attr(Attributes.data(Attributes.Active), "true") (
      color.white,

      &.before(
        opacity(1),
        transform := "scaleY(1)"
      ),

      &.after(
        opacity(1),
        transform := "scaleY(1)"
      )
    ),
    MediaQueries.desktop(
      style(
        &.hover(
          color.white,
          textDecoration := "none"
        )
      )
    ),

    &.before(
      StyleUtils.transition(),
      content := "\" \"",
      position.absolute,
      left(`0`),
      bottom(-2 px),
      width(100 %%),
      height :=! "calc(100% + 2px)",
      backgroundColor(StyleConstants.Colors.GreySuperDark),
      zIndex(-1),
      opacity(0)
    ),

    &.after(
      StyleUtils.transition(),
      content := "\" \"",
      position.absolute,
      left(`0`),
      bottom(-1 px),
      width(100 %%),
      height(100 %%),
      borderRadius(4 px, 4 px, `0`, `0`),
      borderColor(StyleConstants.Colors.Grey),
      borderWidth(1 px),
      borderTopStyle.solid,
      borderLeftStyle.solid,
      borderRightStyle.solid,
      zIndex(-1),
      opacity(0),
      transform := "scaleY(.7)",
      transformOrigin := "50% 100%",

      MediaQueries.phone(
        style(
          borderLeftStyle.none,
          borderRightStyle.none,
          borderBottomStyle.solid
        )
      )
    )
  )

  val demoCode = style(
    position.relative,
    textAlign.left,
    height :=! s"calc(100% - 43px)",
    width(100 %%),
    marginRight(50 px),

    MediaQueries.tabletLandscape(
      style(
        height.auto,
        marginRight(`0`)
      )
    )
  )

  val demoIOWrapper = style(
    position.relative,
    maxWidth(480 px),
    margin(`0`, auto),
    top(50 %%),
    borderRadius(6 px),
    overflow.hidden,
    transform := "translate3d(0, 0, 1px) translateY(-50%)",

    MediaQueries.tabletLandscape(
      style(
        top.auto,
        transform := "none"
      )
    ),

    MediaQueries.phone(
      style(
        width(100 %%)
      )
    )
  )

  val demoBootstrap = style(
    height(300 px)
  )

  val demoInlineField = style(
    display.tableCell,
    minHeight(34 px),
    height(100 %%),
    padding(`0`, 1.3 rem),
    border.none,
    verticalAlign.middle,

    MediaQueries.phone(
      style(
        display.block,
        width(100 %%),
        height.auto,
        padding(.7 rem, 1.3 rem),
        textAlign.center
      )
    )
  )

  val demoInput = style(
    padding(.7 rem, 1.3 rem),
    border.none
  )

  val demoOutput = style(
    padding(1 rem, 1.3 rem),
    backgroundColor(StyleConstants.Colors.GreyExtra)
  )

  val demoOutpuLabel = style(
    GlobalStyles.get.col,
    GlobalStyles.get.width33,
    paddingRight(15 px),
    textAlign.right
  )

  val navItem = style(
    color.black,
    position.relative,
    display.inlineBlock,
    verticalAlign.middle,
    paddingLeft(1.8 rem),
    paddingRight(1.8 rem),

    &.firstChild (
      paddingLeft(0 px)
    ),

    &.lastChild (
      paddingRight(0 px)
    ),

    &.before.not(_.firstChild)(
      StyleUtils.absoluteMiddle,
      content := "\"|\"",
      left(`0`),

      &.hover(
        textDecoration := "none"
      )
    )
  )

  val underlineLink = style(
    GlobalStyles.get.underlineLink,
    color.black
  )(Compose.trust)
}
