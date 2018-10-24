package io.udash.bindings.inputs

import io.udash._
import io.udash.properties.PropertyCreator
import io.udash.properties.seq.SeqProperty
import org.scalajs.dom.{Element, Event, Node}
import org.scalajs.dom.html.{Div, Input => JSInput}
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._

/** Checkboxes for finite options with many elements selection. Bound to SeqProperty. */
object CheckButtons {
  def inputsOnlyDecorator[T]: Seq[(JSInput, T)] => Seq[Node] =
    RadioButtons.inputsOnlyDecorator
  def spanWithLabelDecorator[T](labelFactory: T => Modifier): Seq[(JSInput, T)] => Seq[Node] =
    RadioButtons.spanWithLabelDecorator(labelFactory)
  def divWithLabelDecorator[T](labelFactory: T => Modifier): Seq[(JSInput, T)] => Seq[Node] =
    RadioButtons.divWithLabelDecorator(labelFactory)

  /**
    * @param selectedItems SeqProperty which is going to be bound to checkboxes
    * @param options Seq of available options, one checkbox will be created for each option.
    * @param decorator Function creating HTML element from checkboxes Seq.
    * @param inputModifiers Modifiers to apply on each generated checkbox.
    * @return HTML element created by decorator.
    */
  def apply[T : PropertyCreator](
    selectedItems: SeqProperty[T, _ <: ReadableProperty[T]], options: ReadableSeqProperty[T]
  )(decorator: Seq[(JSInput, T)] => Seq[Node], inputModifiers: Modifier*): InputBinding[Div] = {
    new GroupedButtonsBinding(options, decorator, inputModifiers)(
      "checkbox",
      opt => selectedItems.transform(_.contains(opt)),
      opts => selectedItems.set(selectedItems.get.filter(opts.contains)),
      (in: JSInput, opt: T) => (_: Event) => {
        if (in.checked && !selectedItems.get.contains(opt)) selectedItems.append(opt)
        else selectedItems.remove(opt)
      }
    )
  }

  /**
    * @param property SeqProperty which is going to be bound to checkboxes
    * @param options Seq of available options, one checkbox will be created for each option.
    * @param decorator Function creating HTML element from checkboxes Seq.
    * @param xs Modifiers to apply on each generated checkbox.
    * @return HTML element created by decorator.
    */
  @deprecated("Use the constructor with dynamic options set and generic element type.", "0.7.0")
  def apply(
    property: SeqProperty[String, _ <: ReadableProperty[String]], options: Seq[String],
    decorator: Seq[(JSInput, String)] => TypedTag[Element], xs: Modifier*
  ): TypedTag[Element] = {
    val htmlInputs = prepareHtmlInputs(options)(xs: _*)
    val bind = prepareBind(property)
    htmlInputs.foreach(bind.applyTo)
    decorator(htmlInputs.zip(options))
  }

  private def prepareHtmlInputs(options: Seq[String])(xs: Modifier*): Seq[JSInput] =
    options.map(opt => input(tpe := "checkbox", value := opt)(xs: _*).render)

  private def prepareBind(property: SeqProperty[String, _ <: ReadableProperty[String]]): Modifier = {
    def updateInput(t: JSInput): Unit = {
      val selection = property.get
      t.checked = selection.contains(t.value)
    }

    new Modifier {
      override def applyTo(t: Element): Unit = {
        val element = t.asInstanceOf[JSInput]

        updateInput(element)
        property.listen(_ => updateInput(element))
        element.onchange = (_: Event) => {
          val value = element.value
          if (element.checked) property.append(value)
          else property.remove(value)
        }
      }
    }
  }
}
