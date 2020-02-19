Feature: brief description of what is being tested
    more lines of description if needed.

Scenario: case
	* def Context = Java.type('eu.k5.greenfield.karate.Context')
	* def ctx = new Context()

	* def createResource = ctx.createStep("createResource").url("http://localhost:8080/resource").request("{ \"value\": \"value\"}\n")
	* def getResource = ctx.createStep("getResource").url("http://localhost:8080/resource/${#Project#projectProperty}")
	* def updateResource = ctx.createStep("updateResource").url("http://localhost:8080/resource/${#Project#projectProperty}").request("{ \"id\":\"${#Project#projectProperty}\", \"value\": \"updated\" }\n")

	# Properties
	# createResource
	Given url createResource.url()
	  And request createResource.request()
	  And param queryParam = ctx.applyProperties("")
	  And header Accept = "application/json"
	  And header Content-Type = "application/json"
	  And header headerP = ctx.applyProperties("headerV")
	When method POST
	Then def t0 = createResource.response(response)
	  And status 200
	  And match createResource.assertJsonExists("$.id") == true

	# transfer
	* def t1 = ctx.transfer("#createResource#Response","$.id","#Project#projectProperty","")
	* def t2 = ctx.transfer("#createResource#Response","$.id","#TestSuite#suiteProperty","")
	* def t3 = ctx.transfer("#createResource#Response","$.id","#TestCase#caseProperty","")

	# getResource
	Given url getResource.url()
	  And param queryParam = ctx.applyProperties("")
	  And header Accept = "application/json"
	  And header Content-Type = "application/json"
	When method GET
	Then def t4 = getResource.response(response)
	  And match getResource.assertJsonExists("$.id") == true

	# Delay
	* def t5 = ctx.sleep(1000)

	# updateResource
	Given url updateResource.url()
	  And request updateResource.request()
	  And param queryParam = ctx.applyProperties("")
	  And header Accept = "application/json"
	  And header Content-Type = "application/json"
	When method PUT
	Then def t6 = updateResource.response(response)
	  And match updateResource.assertJsonExists("$.id") == true

	# Script Groovy
	* text t7 =
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
	* def t8 = ctx.groovy(t7)
