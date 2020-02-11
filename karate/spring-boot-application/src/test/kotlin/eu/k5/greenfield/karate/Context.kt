package eu.k5.greenfield.karate

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import java.lang.StringBuilder

class Context {
    private val project = GenericContext(this)
    private val testSuite = GenericContext(this)
    private val testCase = GenericContext(this)
    private val steps = HashMap<String, StepContext>()

    fun createStep(name: String): StepContext {
        val stepContext = StepContext(name, this)
        steps[name] = stepContext
        return stepContext
    }

    fun transfer(
        sourceProperty: String,
        source: String,
        sourceExpression: String,
        targetProperty: String,
        target: String,
        targetExpression: String
    ): String {

        println("transfer")

        val s = TransferHandler.Transfer(sourceExpression, source, sourceProperty, "JSONPATH")
        println(s)

        val sourceValue = getSource(s)
        val t = TransferHandler.Transfer(targetExpression, target, targetProperty, "JSONPATH")

        println("target $t")
        updateTarget(t, sourceValue ?: "")

        return ""
    }

    fun updateTarget(target: TransferHandler.Transfer, value: String) {

        if (target.expression.isNullOrEmpty()) {
            setProperty(target.stepName, target.propertyName!!, value)
            return
        }
        TODO("implement")
    }

    private fun setProperty(stepName: String?, propertyName: String, value: String) {
        getContext(stepName)!!.setProperty(propertyName, value)
    }

    private fun getContext(contextName: String?): GenericContext? {
        if (contextName == "Project") {
            return project
        }
        return steps[contextName]
    }

    private fun getProperty(stepName: String?, propertyName: String?): String? {
        return getContext(stepName)?.getProperty(propertyName)
    }

    fun call() {
        println("abc")
    }

    private fun getSource(
        source: TransferHandler.Transfer
    ): String? {
        val property = getProperty(source.stepName, source.propertyName)
        if (source.expression.isNullOrEmpty() || property.isNullOrEmpty()) {
            return property
        }
        return when {
            source.language == "JSONPATH" -> extractJsonPath(property, source.expression!!)
            source.language == "XPATH" -> throw UnsupportedOperationException("xpath")
            source.language == "XQUERY" -> throw UnsupportedOperationException("xquery")
            else -> property
        }
    }

    private fun extractJsonPath(json: String, expression: String): String? {
        return JsonPath.read<String>(json, expression)
    }

    fun getScopedPropery(property: String): String? {
        val parts = property.split("#")
        if (parts.size == 3) {
            return getContext(parts[1])?.getProperty(parts[2]) ?: property
        }
        return property
    }

    fun applyProperties(value: String?): String {
        if (value.isNullOrEmpty()) {
            return value ?: ""
        }

        val resultBuilder = StringBuilder()
        var start = 0
        val matcher = RunnerContext.expression.matcher(value)
        while (matcher.find()) {
            if (start != matcher.start()) {
                val before = value.subSequence(start, matcher.start())
                resultBuilder.append(before)
            }
            val property = matcher.group("property")
            resultBuilder.append(getScopedPropery(property))
            start = matcher.end()
        }
        if (start == 0) {
            return value
        }
        if (start != value.length) {
            resultBuilder.append(value.subSequence(start, value.length))
        }
        return resultBuilder.toString()
    }
}

open class GenericContext(
    val context: Context
) {
    private val properties = HashMap<String, String>()

    fun setProperty(propertyName: String, value: String) {
        properties[propertyName] = value
    }

    fun getProperty(propertyName: String?): String? {
        if (propertyName == null) {
            return null
        }
        return context.applyProperties(properties[propertyName])
    }
}

class StepContext(
    val name: String,
    context: Context
) : GenericContext(context) {

    var url: String? = null
    var request: String? = null

    fun url(url: String): StepContext {
        this.url = url
        setProperty("url", url)
        return this
    }

    fun url(): String = context.applyProperties(url) ?: ""
    fun request(): String = context.applyProperties(request) ?: ""

    fun request(value: String): StepContext {
        request = value
        setProperty("request", value)
        return this
    }

    fun response(value: Any): StepContext {
        println(value)
        val responseString = ObjectMapper().writeValueAsString(value)
        setProperty("response", responseString)
        return this
    }


}
