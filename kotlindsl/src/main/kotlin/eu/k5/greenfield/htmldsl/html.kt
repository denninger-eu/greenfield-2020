package eu.k5.greenfield.htmldsl

import org.w3c.dom.*

class document {

    companion object {
        fun createElement(name: String): Element {
            return Element()
        }

        fun createTextNode(name: String): Element {

        }
    }
}

class Element {
    fun appendChild(element: Element) {

    }

}

abstract class Tag(val name: String) {
    val element = document.createElement(name)
    protected fun <T : Tag> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        element.appendChild(tag.element)
        return tag
    }
}

abstract class TagWithText(name: String) : Tag(name) {
    operator fun String.unaryPlus() {
        element.appendChild(document.createTextNode(this + " "))
    }
}

class HTML() : TagWithText("html") {
    fun head(init: Head.() -> Unit) = initTag(Head(), init)
    fun body(init: Body.() -> Unit) = initTag(Body(), init)
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}