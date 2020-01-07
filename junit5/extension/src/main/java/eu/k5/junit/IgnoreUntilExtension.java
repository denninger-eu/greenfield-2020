package eu.k5.junit;

import java.lang.reflect.Method;
import java.time.LocalDate;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class IgnoreUntilExtension implements ExecutionCondition {

	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		System.out.println("extension");
		Method method = context.getTestMethod().get();
		IgnoreUntil annotation = method.getAnnotation(IgnoreUntil.class);

		context.publishReportEntry("ignored", "abcasd");
		
		String value = annotation.value();
		LocalDate parse = LocalDate.parse(value);
		LocalDate now = LocalDate.now();
		if (parse.isAfter(now)) {

			return ConditionEvaluationResult.disabled("Ignored until " + value);
		}
		return ConditionEvaluationResult.enabled("Enabled");

	}

}
