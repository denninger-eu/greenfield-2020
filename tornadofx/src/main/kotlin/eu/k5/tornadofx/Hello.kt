package eu.k5.tornadofx

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button

import javafx.scene.layout.StackPane

import javafx.stage.Stage
import tornadofx.*

class HelloWorld : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Hello World!"
        val btn = Button()
        btn.setText("Say 'Hello World'")
        btn.setOnAction { println("Hello World!") }
        val root = StackPane()
        root.children.add(btn)
        primaryStage.scene = Scene(root, 300.0, 250.0)
        primaryStage.show()
    }

    companion object {
        /*        fun main(vararg args: String) {
                    Application.launch(HelloWorld::class.java, *args)
                }*/
        @JvmStatic
        fun main(args: Array<String>) {
            launch<MyApp>(*args)

        }
    }
}