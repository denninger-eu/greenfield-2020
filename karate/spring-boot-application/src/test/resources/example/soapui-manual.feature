Feature: brief description of what is being tested
    more lines of description if needed.

Scenario: brief description of this scenario
  * def Context = Java.type('eu.k5.greenfield.karate.Context');
  * def ctx = new Context()

  * def tx = ctx.setProperty("prop", "key", "value")

  * def createResourceStep = ctx.createStep("createResource").url( "http://localhost:8080/resource" ).request(  read("soapui-manual-createResourceStep.json"))
  * def getResourceStep = ctx.createStep("getResource").url("http://localhost:8080/resource/${#Project#projectProperty}")
  * def updateResourceStep = ctx.createStep("updateResource").url("http://localhost:8080/resource/${#Project#projectProperty}").request("{ \"id\":\"${#Project#projectProperty}\", \"value\": \"updated\" }")

  Given url createResourceStep.url()
  And request createResourceStep.request()
  And header Accept = 'application/json'
  And header Content-Type = 'application/json'
  When method POST
  Then def temp = createResourceStep.response(response)
  And status 200
  And match createResourceStep.assertJsonExists("$.id") == true

  * def t = ""
  * set t = ctx.transfer("#createResource#Response", "$.id", "#Project#projectProperty", "")
  * set t = ctx.transfer("#createResource#Response", "$.id", "#Project#projectProperty", "")

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

  * def t6 = ctx.groovy( read( "groovy.groovy" ) )





