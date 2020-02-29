Feature: brief description of what is being tested
    more lines of description if needed.

Scenario: case
	* def Context = Java.type('eu.k5.dread.karate.RunnerContext')
	* def ctx = new Context()

	* def Properties = ctx.propertiesStep("Properties")
	* print Properties.setProperty("key","keyValue")
	* print Properties.setProperty("date","${=java.time.LocalDateTime.now()}")
	* print Properties.setProperty("dynamicScript","\"test\"")
	* def createResource = ctx.requestStep("createResource").url("http://localhost:8080/resource").request("{ \"value\": \"${=\"String\"x}\"}\n")
	* def getResource = ctx.requestStep("getResource").url("http://localhost:8080/resource/${#Project#projectProperty}")
	* def updateResource = ctx.requestStep("updateResource").url("http://localhost:8080/resource/${#Project#projectProperty}").request("{ \"id\":\"${#Project#projectProperty}\", \"value\": \"updated\" }\n")
	# Script Groovy
	* text t0 =
"""
// Get a test case property
def testCaseProperty = testRunner.testCase.getPropertyValue("MyProp")
// Get a test suite property
def testSuiteProperty = testRunner.testCase.testSuite.getPropertyValue( "MyProp" )
// Get a project property
def projectProperty = testRunner.testCase.testSuite.project.getPropertyValue( "MyProp" )
// Get a global property
def globalProperty = com.eviware.soapui.SoapUI.globalProperties.getPropertyValue( "MyProp" )

def someValue = "value"

// Set a test case property
testRunner.testCase.setPropertyValue( "MyProp", someValue )
// Set a test suite property
testRunner.testCase.testSuite.setPropertyValue( "MyProp", someValue )
// Set a project property
testRunner.testCase.testSuite.project.setPropertyValue( "MyProp", someValue )
// Set a global property
com.eviware.soapui.SoapUI.globalProperties.setPropertyValue( "MyProp", someValue )
"""
	* def Groovy = ctx.groovyScript("Groovy").script(t0)

	# Script Groovy2
	* text t1 =
"""
"test"
"""
	* def Groovy2 = ctx.groovyScript("Groovy2").script(t1)


	# Properties
	# createResource
	Given url createResource.url()
	  And request createResource.request()
	  And param queryParam = ""
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
	* print ctx.transfer("#Properties#dynamicScript").to("#Groovy2#script")

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

	# TransferScriptResult
	* print ctx.transfer("#Groovy2#result").to("#TestSuite#suiteProperty")
