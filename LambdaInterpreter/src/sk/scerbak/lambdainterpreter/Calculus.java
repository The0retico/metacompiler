package sk.scerbak.lambdainterpreter;

/**
 * Library, Facade and Fluent API for abstract syntax of lambda expressions.
 * 
 * @author The0retico
 * 
 */
/**
 * @author The0retico
 *
 */
/**
 * @author The0retico
 * 
 */
public final class Calculus {

	/**
	 * Helper class to provide nicer syntax for defining new abstractions.
	 * 
	 * @author The0retico
	 */
	public static final class Definition {
		static Definition define(final String[] variableNames) {
			return new Definition(variableNames);
		}

		/**
		 * Variables for nested abstraction this definition captures.
		 */
		private final String[] variables;

		/**
		 * @param variableNames
		 *            to be captured in nested abstractions.
		 */
		private Definition(final String[] variableNames) {
			variables = variableNames;
		}

		/**
		 * @param function
		 *            for application in nested abstraction of this defnition
		 * @param arguments
		 *            for nested application in nested abstraction
		 * @return nested lambda abstraction according to captured definition
		 *         containing an appication
		 */
		public IExpression apply(final IExpression function,
				final IExpression... arguments) {
			return def(vars(variables), Calculus.apply(function, arguments));
		}

		/**
		 * @param constantName
		 *            to be returned from abstraction
		 * @return abstraction returning a constant
		 */
		public IExpression con(final String constantName) {
			return def(vars(variables), Calculus.con(constantName));
		}

		/**
		 * @param naturalNumber
		 *            to be returned in abstraction
		 * @return abstraction returning a natural number
		 */
		public IExpression nat(final Integer naturalNumber) {
			return def(vars(variables), Calculus.nat(naturalNumber));
		}

		/**
		 * @param variableName
		 *            to be returned
		 * @return abstraction returning a variable
		 */
		public IExpression var(final String variableName) {
			return def(vars(variables), Calculus.var(variableName));
		}
	}

	/**
	 * @param function
	 *            inner-most left-most expression in nested applications
	 * @param arguments
	 *            nested lambda expression from left to right
	 * @return new lambda application
	 */
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

	/**
	 * @param constantName
	 *            name of the constant
	 * @return new lambda constant
	 */
	public static IExpression con(final String constantName) {
		return new Constant(constantName);
	}

	/**
	 * @param variableNames
	 *            for nested abstractions
	 * @return new definition helper for more convenient creation of nested
	 *         abstractions
	 */
	public static Definition def(final String... variableNames) {
		return Definition.define(variableNames);
	}

	/**
	 * @param variable
	 *            for this abstraction
	 * @param body
	 *            body of an abstraction
	 * @return new lambda abstraction with variable and body
	 */
	public static IExpression def(final String variable, final IExpression body) {
		return def(new String[] { variable }, body);
	}

	/**
	 * @param variables
	 *            in order in which they will be in nested abstractions
	 * @param body
	 *            for the bottom level abstraction
	 * @return nested lambda abstractions
	 */
	public static IExpression def(final String[] variables,
			final IExpression body) {
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

	/**
	 * @param naturalNumber
	 *            value of the natural number
	 * @return new lambda natural number
	 */
	public static IExpression nat(final Integer naturalNumber) {
		return new Natural(naturalNumber);
	}

	/**
	 * @param variableName
	 *            for lambda variable
	 * @return ne lambda variable
	 */
	public static IExpression var(final String variableName) {
		return new Variable(variableName);
	}

	/**
	 * Helper function to allow variable number of arguments not as a last
	 * parameter.
	 * 
	 * @param variables
	 *            names of variables
	 * @return array containing names of variables
	 */
	public static String[] vars(final String... variables) {
		if (variables.length < 1) {
			throw new IllegalArgumentException();
		}
		return variables;
	}

	/**
	 * Utility class should not be instantiable.
	 */
	private Calculus() {

	}

}
