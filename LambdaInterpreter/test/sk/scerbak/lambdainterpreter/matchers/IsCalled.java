package sk.scerbak.lambdainterpreter.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import sk.scerbak.lambdainterpreter.Mock;

public class IsCalled<T> extends BaseMatcher<T> {

	private final String method;

	public IsCalled(final String methodName) {
		method = methodName;
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText("isCalled");
	}

	@Override
	public boolean matches(final Object item) {
		if (item instanceof Mock) {
			final Mock mock = (Mock) item;
			return mock.getMethodCalls().contains(method);
		} else {
			return false;
		}
	}
}
