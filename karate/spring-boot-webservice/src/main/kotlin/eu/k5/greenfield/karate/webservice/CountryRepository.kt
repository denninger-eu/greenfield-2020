package eu.k5.greenfield.karate.webservice

import eu.k5.greenfield.karate.webservice.Country
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import kotlin.collections.HashMap

@Component
class CountryRepository {
    private val countries = HashMap<String?, Country>()

    @PostConstruct
    fun initData() {
        val spain = Country()
        spain.name = "Spain"
        spain.capital = "Madrid"
        spain.population = 46704314

        countries.put(spain.name, spain)

        val poland = Country()
        poland.name = "Poland"
        poland.capital = "Warsaw"
        poland.population = 38186860

        countries.put(poland.name, poland)

        val uk = Country()
        uk.name = "United Kingdom"
        uk.capital = "London"
        uk.population = 63705000

        countries.put(uk.name, uk)
    }

    fun findCountry(name: String): Country? {
        return countries[name]
    }
}