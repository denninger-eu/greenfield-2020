package eu.k5.tornadofx

import tornadofx.*

class MyView : View() {

    override val root = vbox {
        button("Press me") {
            action { println("test") }
        }
        label("Waiting")
    }
}