package io.udash.bindings

import org.scalajs.dom.Node

package object modifiers {
  implicit class ElementExts(private val el: Node) extends AnyVal {
    def replaceChildren(oldChildren: Seq[Node], newChildren: Seq[Node]): Unit = {
      if (oldChildren == null || oldChildren.isEmpty) newChildren.foreach(el.appendChild)
      else {
        oldChildren.zip(newChildren).foreach { case (old, fresh) => el.replaceChild(fresh, old) }
        oldChildren.drop(newChildren.size).foreach(el.removeChild)
        newChildren.drop(oldChildren.size - 1)
          .sliding(2).foreach(s =>
            if (s.size == 2) el.insertBefore(s(1), s(0).nextSibling)
          )
      }
    }
  }
}
