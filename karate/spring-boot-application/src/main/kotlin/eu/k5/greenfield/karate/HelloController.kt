package eu.k5.greenfield.karate


import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.websocket.server.PathParam


@RestController
open class HelloController {


    @GetMapping("/resource")
    fun getResource(): List<Res> {
        return ArrayList(repository.values)
    }

    @RequestMapping(value = ["/resource"], method = [RequestMethod.POST])
    fun postResource(@RequestBody resource: Res): Res {
        val id = UUID.randomUUID().toString()
        resource.id = id
        println(resource.value)

        repository[id] = resource
        return resource
    }

    @RequestMapping(value = ["/resource/{id}"], method = [RequestMethod.PUT])
    fun putResource(@PathVariable("id") id: String, @RequestBody resource: Res): Res {
        println(resource)
        repository[id] = resource
        return resource
    }

    @RequestMapping(value = ["/resource/{id}"], method = [RequestMethod.GET])
    fun getResource(@PathVariable("id") id: String): Res {
        return repository[id]!!
    }

    companion object {
        val repository = ConcurrentHashMap<String, Res>()
    }


}

data class Res(
    var id: String? = null,
    var value: String? = null
) {
    constructor() : this(null, null)
}
