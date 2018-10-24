package io.udash.bindings.inputs

import com.github.ghik.silencer.silent
import io.udash._
import io.udash.testing.UdashFrontendTest

class CheckboxTest extends UdashFrontendTest {
  "Checkbox" should {
    "synchronise state with property changes (deprecated)" in {
      val p = Property[Boolean](true)
      val checkbox = (Checkbox.deprecated(p): @silent).render

      checkbox.checked should be(true)

      p.set(false)
      checkbox.checked should be(false)

      p.set(true)
      checkbox.checked should be(true)

      p.set(false)
      checkbox.checked should be(false)
    }

    "synchronise property with state changes (deprecated)" in {
      val p = Property[Boolean](true)
      val checkbox = (Checkbox.deprecated(p): @silent).render

      checkbox.checked should be(true)

      checkbox.checked = false
      checkbox.onchange(null)
      p.get should be(false)

      checkbox.checked = true
      checkbox.onchange(null)
      p.get should be(true)

      checkbox.checked = false
      checkbox.onchange(null)
      p.get should be(false)
    }

    "synchronise state with property changes" in {
      val p = Property[Boolean](true)
      val checkbox = Checkbox(p)()
      val checkboxEl = checkbox.render

      checkboxEl.checked should be(true)

      p.set(false)
      checkboxEl.checked should be(false)

      p.set(true)
      checkboxEl.checked should be(true)

      p.set(false)
      checkboxEl.checked should be(false)

      p.listenersCount() should be(1)
      checkbox.kill()
      p.listenersCount() should be(0)
    }

    "synchronise property with state changes" in {
      val p = Property[Boolean](true)
      val checkbox = Checkbox(p)()
      val checkboxEl = checkbox.render

      checkboxEl.checked should be(true)

      checkboxEl.checked = false
      checkboxEl.onchange(null)
      p.get should be(false)

      checkboxEl.checked = true
      checkboxEl.onchange(null)
      p.get should be(true)

      checkboxEl.checked = false
      checkboxEl.onchange(null)
      p.get should be(false)

      p.listenersCount() should be(1)
      checkbox.kill()
      p.listenersCount() should be(0)
    }

    "synchronise with two inputs bound to a single property" in {
      val p = Property[Boolean](true)
      val input = Checkbox(p)()
      val input2 = Checkbox(p)()

      val r = input.render
      val r2 = input2.render

      r.checked should be(true)
      r2.checked should be(true)

      p.set(false)
      r.checked should be(false)
      r2.checked should be(false)

      r.checked = true
      r.onchange(null)
      p.get should be(true)
      r2.checked should be(true)

      r2.checked = false
      r2.onchange(null)
      p.get should be(false)
      r.checked should be(false)

      p.listenersCount() should be(2)

      input2.kill()
      p.listenersCount() should be(1)

      r.checked = true
      r.onchange(null)
      p.get should be(true)
      r2.checked should be(false)

      input.kill()
      p.listenersCount() should be(0)

      p.set(false)
      r.checked should be(true)
      r2.checked should be(false)
    }
  }
}
