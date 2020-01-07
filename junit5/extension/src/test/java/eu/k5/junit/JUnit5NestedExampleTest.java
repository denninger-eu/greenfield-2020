package eu.k5.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("JUnit 5 Nested Example")
class JUnit5NestedExampleTest {

	public JUnit5NestedExampleTest() {
		System.out.println("new instance");
	}

	@BeforeAll
	static void beforeAll() {
		System.out.println("Before all test methods");
	}

	@BeforeEach
	void beforeEach() {
		System.out.println("Before each test method");
	}

	@AfterEach
	void afterEach() {
		System.out.println("After each test method");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("After all test methods");
	}
	
	@Test
	void test() {
		System.out.println("main test");
	}

	@Nested
	@DisplayName("Tests for the method A")
	class A {

		@BeforeEach
		void beforeEach() {
			System.out.println("Before each test method of the A class");
		}

		@AfterEach
		void afterEach() {
			System.out.println("After each test method of the A class");
		}

		@Test
		@DisplayName("Example test for method A")
		void sampleTestForMethodA() {
			System.out.println("Example test for method A");
		}
		
		@Test
		@DisplayName("Example test for method X")
		void sampleTestForMethodX() {
			System.out.println("Example test for method X");
		}
	}
}