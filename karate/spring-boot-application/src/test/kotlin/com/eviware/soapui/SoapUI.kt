package com.eviware.soapui

import eu.k5.greenfield.karate.Context
import eu.k5.greenfield.karate.PropertyHolder
import org.slf4j.LoggerFactory

class SoapUIX {

    companion object {
        @JvmStatic
        val globalProperties = PropertyHolder()
    }
}

class PropertyHolder(
) {
    val logger = LoggerFactory.getLogger(com.eviware.soapui.PropertyHolder::class.java)

    private val properties = HashMap<String, String>()


    fun getPropertyValue(name: String): String? {
        return properties[name]
    }

    fun setPropertyValue(name: String, value: String) {
        logger.info("Set property '{}' to value '{}'", name, value)
        properties[name] = value
    }
}