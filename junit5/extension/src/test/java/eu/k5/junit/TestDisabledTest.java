package eu.k5.junit;

import org.junit.jupiter.api.Test;

public class TestDisabledTest {

	@Test
	public void testAbc() {

	}

	@Test
	@IgnoreUntil("2020-01-04")
	public void testAsdasd() {

		System.out.println("Test run");
		throw new RuntimeException();
	}
}
