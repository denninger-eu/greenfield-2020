Feature: brief description of what is being tested
    more lines of description if needed.

Scenario: case
	* def Context = Java.type('eu.k5.greenfield.karate.Context')
	* def ctx = new Context()

	* def createResource = ctx.createStep("createResource").url("http://localhost:8080/resource").request("{ \"value\": \"value\"}\n")
	* def getResource = ctx.createStep("getResource").url("http://localhost:8080/resource/${#Project#projectProperty}")
	* def updateResource = ctx.createStep("updateResource").url("http://localhost:8080/resource/${#Project#projectProperty}").request("{ \"id\":\"${#Project#projectProperty}\", \"value\": \"updated\" }\n")

	# createResource
	Given url createResource.url()
	  And request createResource.request()
	  And header queryParam = ctx.applyProperties("")
	  And header Accept = "application/json"
	  And header Content-Type = "application/json"
	  And header headerP = ctx.applyProperties("headerV")
	When method POST
	Then def t0 = createResource.response(response)
	 And status 200

	# transfer
	* def t1 = ctx.transfer("#createResource#Response","$.id","#Project#projectProperty","")
	* def t2 = ctx.transfer("#createResource#Response","$.id","#TestSuite#suiteProperty","")
	* def t3 = ctx.transfer("#createResource#Response","$.id","#TestCase#caseProperty","")

	# getResource
	Given url getResource.url()
	  And header queryParam = ctx.applyProperties("")
	  And header Accept = "application/json"
	  And header Content-Type = "application/json"
	When method GET
	Then def t4 = getResource.response(response)
	 And status 200

	# updateResource
	Given url updateResource.url()
	  And request updateResource.request()
	  And header queryParam = ctx.applyProperties("")
	  And header Accept = "application/json"
	  And header Content-Type = "application/json"
	When method PUT
	Then def t5 = updateResource.response(response)
	 And status 200



