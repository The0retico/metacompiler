package sk.scerbak.lambdainterpreter;

import org.hamcrest.Matcher;

import sk.scerbak.lambdainterpreter.matchers.IsCalled;

public class LambdaMatchers {

	public static Matcher isCalled(final String methodName) {
		return new IsCalled(methodName);
	}
}
