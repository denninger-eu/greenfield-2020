package eu.k5.greenfield.karate.webservice

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "country", namespace = "http://spring.io/guides/gs-producing-web-service")
@XmlAccessorType(XmlAccessType.NONE)
class Country {
    @XmlElement
    var name: String? = null
    @XmlElement
    var population: Int? = null
    @XmlElement
    var capital: String? = null

}