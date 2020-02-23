package eu.k5.greenfield.karate

import com.intuit.karate.junit5.Karate

class SampleTest {

    //@Karate.Test
    fun testManual(): Karate {
        return Karate().feature("classpath:example/soapui-manual.feature")
      //  return Karate().feature("classpath:example/exp.feature")

    }

    @Karate.Test
    fun testManualWithDread(): Karate {
        return Karate().feature("classpath:example/soapui-manual-withdread.feature")
        //  return Karate().feature("classpath:example/exp.feature")

    }
/*
    @Karate.Test
    fun testFullPath(): Karate {
        return Karate().feature("classpath:example/example.feature")
    }
*/

}