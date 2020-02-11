package eu.k5.greenfield.karate


class TransferHandler {


    private fun updateTarget(
        context: RunnerContext,
        value: String
    ) {
/*
if (target.expression.isNullOrEmpty()) {
    context.addProperty(target.stepName + target.propertyName, value)
    return
}*/
/*
when {
    target.language == SuuPropertyTransfer.Language.JSONPATH -> updateJsonPath()
}
*/
        TODO("implement")
    }




    private fun updateJsonPath(json: String, expression: String): String {
        return json
    }


    data class Transfer(
        val expression: String?,
        val propertyName: String?,
        val stepName: String?,
        val language: String?
    )
}