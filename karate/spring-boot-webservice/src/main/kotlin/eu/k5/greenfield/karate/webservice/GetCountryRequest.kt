package eu.k5.greenfield.karate.webservice

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "getCountryRequest", namespace = "http://spring.io/guides/gs-producing-web-service")
@XmlAccessorType(XmlAccessType.NONE)
class GetCountryRequest {

    @XmlElement(name = "name", namespace = "http://spring.io/guides/gs-producing-web-service")
    var name: String? = null

}
