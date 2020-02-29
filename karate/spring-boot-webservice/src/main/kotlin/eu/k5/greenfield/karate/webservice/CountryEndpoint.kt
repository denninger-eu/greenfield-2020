package eu.k5.greenfield.karate.webservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload


@Endpoint
open class CountryEndpoint @Autowired
constructor(private val countryRepository: CountryRepository) {

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    open fun getCountry(@RequestPayload request: GetCountryRequest): GetCountryResponse {
        val response = GetCountryResponse()
        response.country = countryRepository.findCountry(request.name ?: "")
        return response
    }

    companion object {
        private const val NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service"
    }
}