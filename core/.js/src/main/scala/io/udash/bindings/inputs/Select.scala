package io.udash.bindings.inputs

import io.udash._
import io.udash.properties.PropertyCreator
import io.udash.properties.seq.SeqProperty
import org.scalajs.dom.html.{Option, Select}
import org.scalajs.dom.raw.HTMLOptionElement
import org.scalajs.dom.{Element, Event}
import scalatags.JsDom
import scalatags.JsDom.all._

/**
  * Select of finite options for single and multi selection.
  */
object Select {
  val defaultLabel: String => Modifier = s => StringFrag(s)

  /**
    * Single select for ValueProperty.
    *
    * @param selectedItem Property to bind.
    * @param options SeqProperty of available options.
    * @param label Provides element's label.
    * @param selectModifiers Additional Modifiers for the select tag, don't use modifiers on value, onchange and selected attributes.
    * @return Binding with `select` element, which can be used as Scalatags modifier.
    */
  def apply[T : PropertyCreator](
    selectedItem: Property[T], options: ReadableSeqProperty[T]
  )(label: T => Modifier, selectModifiers: Modifier*): InputBinding[Select] = {
    new SelectBinding(options, label, selectModifiers)(
      opt => selectedItem.transform(_ == opt),
      opts => if (opts.nonEmpty && !opts.contains(selectedItem.get)) selectedItem.set(opts.head),
      selector => (_: Event) => selectedItem.set(options.get.apply(selector.value.toInt))
    )
  }

  /**
    * Multi selection for SeqProperty. Bound SeqProperty will contain selected options.
    *
    * @param selectedItems Property to bind.
    * @param options SeqProperty of available options.
    * @param label Provides element label.
    * @param selectModifiers Additional Modifiers, don't use modifiers on value, onchange and selected attributes.
    * @return Binding with `select` element, which can be used as Scalatags modifier.
    */
  def apply[T : PropertyCreator, ElemType <: Property[T]](
    selectedItems: SeqProperty[T, ElemType], options: ReadableSeqProperty[T]
  )(label: T => Modifier, selectModifiers: Modifier*): InputBinding[Select] = {
    new SelectBinding(options, label, selectModifiers :+ (multiple := true))(
      opt => selectedItems.transform(_.contains(opt)),
      opts => selectedItems.set(selectedItems.get.filter(opts.contains)),
      selector => (_: Event) => {
        val opts = options.get
        val selectedNodes = selector.querySelectorAll("option:checked")
        val selection = (0 until selectedNodes.length).map { idx =>
          opts(selectedNodes(idx).asInstanceOf[HTMLOptionElement].value.toInt)
        }
        selectedItems.set(selection)
      }
    )
  }

  /**
    * Single select for ValueProperty.
    *
    * @param property Property to bind.
    * @param options Seq of available options.
    * @param label Provides element label.
    * @param xs Additional Modifiers, don't use modifiers on value, onchange and selected attributes.
    * @return HTML select tag with bound ValueProperty, applied modifiers and nested options.
    */
  @deprecated("Use the constructor with dynamic options set and generic element type.", "0.7.0")
  def apply(
    property: Property[String], options: Seq[String], label: String => Modifier
  )(xs: Modifier*): JsDom.TypedTag[Select] = {
    val htmlOptions = prepareHtmlOptions(options, label)

    def refreshSelectedItems(): Unit = {
      htmlOptions.foreach { option =>
        option.selected = property.get == option.value
      }
    }

    val bind = new JsDom.Modifier {
      override def applyTo(t: Element): Unit = {
        val element = t.asInstanceOf[Select]

        refreshSelectedItems()
        property.listen(_ => refreshSelectedItems())
        element.onchange = (_: Event) => {
          property.set(htmlOptions.find(o => o.selected).get.value)
        }
      }
    }

    select(bind, xs)(htmlOptions)
  }

  /**
    * Multi selection for SeqProperty. Bound SeqProperty will contain selected options.
    *
    * @param property Property to bind.
    * @param options Seq of available options.
    * @param label Provides element label.
    * @param xs Additional Modifiers, don't use modifiers on value, onchange and selected attributes.
    * @return HTML select tag with bound SeqProperty, applied modifiers and nested options.
    */
  @deprecated("Use the constructor with dynamic options set and generic element type.", "0.7.0")
  def apply(
    property: SeqProperty[String, _ <: ReadableProperty[String]], options: Seq[String], label: String => Modifier
  )(xs: Modifier*): JsDom.TypedTag[Select] = {
    val htmlOptions = prepareHtmlOptions(options, label)

    def refreshSelectedItems(): Unit = {
      val selection = property.get
      htmlOptions.foreach { option =>
        option.selected = selection.contains(option.value)
      }
    }

    def collectSelectedItems: Seq[String] = {
      htmlOptions.filter(option => option.selected).map(option => option.value)
    }

    val bind = new JsDom.Modifier {
      override def applyTo(t: Element): Unit = {
        val element = t.asInstanceOf[Select]

        refreshSelectedItems()
        property.listen(_ => refreshSelectedItems())
        element.onchange = (event: Event) => property.set(collectSelectedItems)
      }
    }

    select(multiple := true, bind, xs)(htmlOptions)
  }

  private def prepareHtmlOptions(options: Seq[String], label: String => Modifier): Seq[Option] =
    options.map { opt =>
      option(value := opt)(label(opt)).render
    }
}
