package eu.k5.junit;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class NestedClassesTest {

	@Test
	public void test() {

		System.out.println("test");

	}

	@Nested
	public  class NestedTest {

		@Test
		public void inner() {
			fail("inner");
		}
	}
}
