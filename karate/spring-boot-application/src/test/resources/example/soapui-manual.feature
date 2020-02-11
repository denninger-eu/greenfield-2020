Feature: brief description of what is being tested
    more lines of description if needed.

Scenario: brief description of this scenario
  * def Context = Java.type('eu.k5.greenfield.karate.Context');
  * def ctx = new Context()

  * def createResourceStep = ctx.createStep("createResource").url( "http://localhost:8080/resource" ).request(  "{ \"value\": \"value\"}" )
  * def getResourceStep = ctx.createStep("getResource").url("http://localhost:8080/resource/${#Project#projectProperty}")
  * def updateResourceStep = ctx.createStep("updateResource").url("http://localhost:8080/resource/${#Project#projectProperty}").request("{ \"id\":\"${#Project#projectProperty}\", \"value\": \"updated\" }")

  Given url createResourceStep.url()
  And request createResourceStep.request()
  And header Accept = 'application/json'
  And header Content-Type = 'application/json'
  When method POST
  Then def temp = createResourceStep.response(response)

  * def t = ctx.transfer("createResource", "response", "$.id", "Project", "projectProperty", "")

  Given url getResourceStep.url()
  And header Accept = 'application/json'
  And header Content-Type = 'application/json'
  When method GET
  Then def t3 = getResourceStep.response(response)

  Given url updateResourceStep.url()
  And request updateResourceStep.request()
  And header Accept = 'application/json'
  And header Content-Type = 'application/json'
  When method PUT
  Then def temp = updateResourceStep.response(response)





