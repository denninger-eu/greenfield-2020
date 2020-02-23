package eu.k5.greenfield.karate

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.PathNotFoundException
import com.sun.org.glassfish.external.amx.AMXUtil
import sun.misc.IOUtils
import java.io.InputStream
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import javax.script.ScriptEngineManager

class Context {
    private val scriptEngineManager: ScriptEngineManager by lazy { ScriptEngineManager() }
    private val scriptEngine by lazy { scriptEngineManager.getEngineByName("groovy") }


    private val project = GenericContext(this)
    private val testSuite = GenericContext(this)
    private val testCase = GenericContext(this)
    private val steps = HashMap<String, StepContext>()

    fun createStep(name: String): StepContext {
        val stepContext = StepContext(name, this)
        steps[name] = stepContext
        return stepContext
    }

    fun transfer(source: String, sourceExpression: String, target: String, targetExpression: String): Any? {
        val s = Transfer(source, sourceExpression, "JSONPATH")
        val sourceValue = getSourceValue(s)
        val t = Transfer(target, targetExpression, "JSONPATH")

        println("target $t")
        updateTarget(t, sourceValue ?: "")
        return null // return HashMap<String,String>()
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

    fun setProperty(name: String, key: String, value: String): String? {
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

    fun getContext(contextName: String?): GenericContext? {
        if (contextName == "Project") {
            return project
        }
        if (contextName == "TestSuite") {
            return testSuite
        }
        if (contextName == "TestCase") {
            return testCase
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

    fun sleep(duration: Int): String {
        Thread.sleep(duration.toLong())
        return ""
    }

    fun groovy(input: InputStream) {
        val script = String(IOUtils.readFully(input, -1, true), StandardCharsets.UTF_8)

        groovy(script)
    }

    fun groovy(script: String) {
        scriptEngine.put("testRunner", TestRunner(this))
        val result = scriptEngine.eval(script)

        println(result)

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

    fun getOrCreateProperty(name: String): Property {
        val property = getProperty("name")
        if (property != null) {
            return property
        }
        return createProperty(name)
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
        setProperty("Response", responseString)
        return this
    }

    fun response(): String {
        return getExpanded("Response") ?: ""
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


class TestRunner(private val context: Context) {

    val testCase = TestCase(context)
}

class TestCase(private val context: Context) {

    private val ctx = context.getContext("TestCase")!!

    val testSuite = TestSuite(context)

    fun getPropertyValue(name: String): String? = ctx.getProperty(name)?.value

    fun setPropertyValue(name: String, value: String) {
        ctx.getOrCreateProperty(name).value = value
    }

}

class TestSuite(private val context: Context) {
    private val ctx = context.getContext("TestSuite")!!

    val project = Project(context)

    fun getPropertyValue(name: String): String? = ctx.getProperty(name)?.value

    fun setPropertyValue(name: String, value: String) {
        ctx.getOrCreateProperty(name).value = value
    }
}

class Project(private val context: Context) {
    private val ctx = context.getContext("Project")!!

    fun getPropertyValue(name: String): String? = ctx.getProperty(name)?.value

    fun setPropertyValue(name: String, value: String) {
        ctx.getOrCreateProperty(name).value = value
    }
}