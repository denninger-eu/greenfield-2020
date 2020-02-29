package eu.k5.greenfield.karate.webservice

import eu.k5.greenfield.karate.webservice.Country
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "getCountryResponse", namespace = "http://spring.io/guides/gs-producing-web-service")
@XmlAccessorType(XmlAccessType.NONE)
class GetCountryResponse {
    
    @XmlElement(name = "country", namespace = "http://spring.io/guides/gs-producing-web-service")
    var country: Country? = null
}
