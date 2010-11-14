package sk.scerbak.lambdainterpreter;

public final class Calcuclus {
	public static IExpression def(String[] variables, final IExpression body) {
		if (variables.length < 1) {
			throw new IllegalArgumentException();
		}
		IExpression result = new Abstraction(variables[variables.length - 1],
				body);
		for (int index = variables.length - 2; index >= 0; index--) {
			result = new Abstraction(variables[index], result);
		}
		return result;
	}

	public static String[] vars(final String... variables) {
		if (variables.length < 1) {
			throw new IllegalArgumentException();
		}
		return variables;
	}

	public static IExpression apply(final IExpression function,
			final IExpression... arguments) {
		if (arguments.length < 1) {
			throw new IllegalArgumentException();
		}
		IExpression result = new Application(function, arguments[0]);
		for (int index = 1; index < arguments.length; index++) {
			result = new Application(result, arguments[index]);
		}
		return result;
	}

	public static IExpression var(final String variableName) {
		return new Variable(variableName);
	}

	public static IExpression con(final String constantName) {
		return new Constant(constantName);
	}

	public static IExpression nat(final Integer naturalNumber) {
		return new Natural(naturalNumber);
	}
}
