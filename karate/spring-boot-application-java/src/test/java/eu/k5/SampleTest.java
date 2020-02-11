package eu.k5;

import com.intuit.karate.junit5.Karate;

public class SampleTest {

    @Karate.Test
    public Karate testFullPath() {
        return new Karate().feature("classpath:karate/tags.feature");
    }

}