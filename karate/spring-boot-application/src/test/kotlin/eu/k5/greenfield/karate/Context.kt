package eu.k5.greenfield.karate

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.PathNotFoundException
import com.sun.org.glassfish.external.amx.AMXUtil
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

    fun transfer(source: String, sourceExpression: String, target: String, targetExpression: String) {
        val s = Transfer(source, sourceExpression, "JSONPATH")
        val sourceValue = getSourceValue(s)
        val t = Transfer(target, targetExpression, "JSONPATH")

        println("target $t")
        updateTarget(t, sourceValue ?: "")
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

        val s = Transfer("#$source#$sourceProperty", sourceExpression, "JSONPATH")
        println(s)

        val sourceValue = getSourceValue(s)
        val t = Transfer("#$target#$targetProperty", targetExpression, "JSONPATH")

        println("target $t")
        updateTarget(t, sourceValue ?: "")

        return ""
    }

    fun updateTarget(target: Transfer, value: String) {
        if (target.expression.isNullOrEmpty()) {
            setProperty(target.entity!!, value)
            return
        }
        TODO("implement")
    }

    private fun setProperty(entityName: String, value: String) {
        getOrCreateProperty(entityName).value = value
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

    private fun getProperty(stepName: String?, propertyName: String?): GenericContext.Property? {
        return getContext(stepName)?.getProperty(propertyName)
    }


    fun call() {
        println("abc")
    }

    private fun getSourceValue(
        source: Transfer
    ): String? {
        val property = getProperty(source.entity) ?: return null

        if (source.expression.isNullOrEmpty() || property.value.isNullOrEmpty()) {
            return property.value
        }
        return when {
            source.language == "JSONPATH" -> extractJsonPath(property.value ?: "", source.expression!!)
            source.language == "XPATH" -> throw UnsupportedOperationException("xpath")
            source.language == "XQUERY" -> throw UnsupportedOperationException("xquery")
            else -> property.value
        }
    }

    private fun extractJsonPath(json: String, expression: String): String? {
        return JsonPath.read<String>(json, expression)
    }

    fun getProperty(property: String): GenericContext.Property? {
        val parts = property.split("#")
        if (parts.size == 3) {
            return getContext(parts[1])?.getProperty(parts[2])
        }
        return null
    }

    fun createProperty(propertyName: String): GenericContext.Property {
        val parts = propertyName.split("#")
        if (parts.size == 3) {
            val context = getContext(parts[1])
            if (context == null) {
                throw IllegalArgumentException("Context not found: " + parts[1])
            }
            return context.createProperty(parts[2])
        }
        throw IllegalArgumentException("Unsupported format")
    }

    private fun getOrCreateProperty(entityName: String): GenericContext.Property {
        val property = getProperty(entityName)
        if (property != null) {
            return property
        }
        return createProperty(entityName)
    }

    fun getExpandedProperty(propertyName: String): String? {
        val property = getProperty(propertyName)
        if (property == null || property.value == null) {
            return propertyName
        }
        return applyProperties(property.value)
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
            resultBuilder.append(getExpandedProperty(property))
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

    data class Transfer(
        val entity: String,
        val expression: String?,
        val language: String?
    )
}

open class GenericContext(
    val context: Context
) {
    private val properties = HashMap<String, Property>()

    fun setProperty(propertyName: String, value: String) {

        val property = properties[propertyName]
        if (property == null) {
            properties[propertyName] = Property(value)
        } else {
            property.value = value
        }
    }

    fun getProperty(propertyName: String?): Property? = properties[propertyName]


    fun getExpanded(propertyName: String?): String? {
        if (propertyName == null) {
            return null
        }
        return context.applyProperties(properties[propertyName]?.value)
    }

    fun createProperty(propertyName: String): Property {
        properties[propertyName] = Property(null)
        return properties[propertyName]!!
    }

    class Property(var value: String?)
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
    fun request(value: Any): StepContext {
        return request(ObjectMapper().writeValueAsString(value))
    }

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

    fun response(): String {
        return getExpanded("response") ?: ""
    }

    private val jsonResponse: DocumentContext by lazy { JsonPath.parse(response()) }

    fun assertJsonExists(jsonPath: String): Boolean {
        return eval(jsonPath)
    }

    private fun eval(expression: String): Boolean {
        return try {
            val result = jsonResponse.read<Object?>(expression)
            result != null
        } catch (notFound: PathNotFoundException) {
            false
        }
    }
}
