Feature: brief description of what is being tested
    more lines of description if needed.

Scenario: case
	* def Context = Java.type('eu.k5.dread.karate.RunnerContext')
	* def ctx = new Context()

	* print ctx.setProperty("#TestCase#baseUrl", "http://localhost:8080")

	* def Properties = ctx.propertiesStep("Properties")
	* print Properties.setProperty("key","keyValue")
	* print Properties.setProperty("date","${=java.time.LocalDateTime.now()}")
	* print Properties.setProperty("dynamicScript","\"test\"")
	* def createResourceRequest = read("createResourceRequest.txt")
	* def createResource = ctx.requestStep("createResource").url("${#TestCase#baseUrl}/resource").request(createResourceRequest)
	* def getResource = ctx.requestStep("getResource").url("${#TestCase#baseUrl}/resource/${#Project#projectProperty}")
	* def updateResourceRequest = read("updateResourceRequest.txt")
	* def updateResource = ctx.requestStep("updateResource").url("${#TestCase#baseUrl}/resource/${#Project#projectProperty}").request(updateResourceRequest)
	# Script Groovy
	* def GroovyScript = read("GroovyScript.groovy")
	* def Groovy = ctx.groovyScript("Groovy").script(GroovyScript)
	
	# Script Groovy2
	* def Groovy2Script = read("Groovy2Script.groovy")
	* def Groovy2 = ctx.groovyScript("Groovy2").script(Groovy2Script)
	
	
	* def Properties = ctx.propertiesStep("Properties")
	* print Properties.setProperty("key","keyValue")
	* print Properties.setProperty("date","${=java.time.LocalDateTime.now()}")
	* print Properties.setProperty("dynamicScript","\"test\"")
	# createResource
	Given url createResource.url()
	  And request createResource.request()
	  And param queryParam = "paramV"
	  And header headerP = "headerV"
	  And header Accept = "application/json"
	  And header Content-Type = "application/json"
	When  method POST
	Then  print createResource.response(response)
	  And status 200
	  And match createResource.assertJsonExists("$.id") == true
	
	# transfer
	* print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#Project#projectProperty")
	* print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#TestSuite#suiteProperty")
	* print ctx.transfer("#createResource#Response","$.id","JSONPATH").to("#TestCase#caseProperty")
	
	# getResource
	Given url getResource.url()
	  And param queryParam = ""
	  And header Accept = "application/json"
	When  method GET
	Then  print getResource.response(response)
	  And match getResource.assertJsonExists("$.id") == true
	
	# Delay
	* print ctx.sleep(1000)
	
	# updateResource
	Given url updateResource.url()
	  And request updateResource.request()
	  And param queryParam = ""
	  And header Accept = "application/json"
	  And header Content-Type = "application/json"
	When  method PUT
	Then  print updateResource.response(response)
	  And match updateResource.assertJsonExists("$.id") == true
	
	# Script Groovy
	* def t2 = Groovy.execute()
	
	# Script Groovy2
	* def t3 = Groovy2.execute()
	
