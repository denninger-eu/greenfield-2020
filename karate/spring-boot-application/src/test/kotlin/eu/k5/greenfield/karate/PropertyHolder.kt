package eu.k5.greenfield.karate

class PropertyHolder (
    private val context: Context,
    private val name: String
) {
    private val ctx = context.getContext(name)!!


    fun getPropertyValue(name: String): String? = ctx.getProperty(name)?.value

    fun setPropertyValue(name: String, value: String) {
        ctx.getOrCreateProperty(name).value = value
    }
}