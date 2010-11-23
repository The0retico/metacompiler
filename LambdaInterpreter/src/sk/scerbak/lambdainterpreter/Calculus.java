package sk.scerbak.lambdainterpreter;

/**
 * Library, Facade and Fluent API for abstract syntax of lambda expressions.
 * 
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
		/**
		 * @param variableNames
		 *            variables for nested abstractions
		 * @return definition object to capture body definition for an
		 *         abstraction
		 */
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

	public static final IExpression SUCC = def("n", "f", "x").apply(var("f"),
			apply(var("n"), var("f"), var("x")));
	public static final IExpression PRED = def("n", "f", "x").apply(var("n"),
			def("g", "h").apply(var("h"), apply(var("g"), var("f"))),
			def("u").var("x"), def("u").var("u"));
	public static final IExpression PLUS = def("m", "n", "f", "x").apply(
			var("m"), var("f"), apply(var("n"), var("f"), var("x")));
	public static final IExpression MULT = def("m", "n", "f").apply(var("n"),
			apply(var("m"), var("f")));
	public static final IExpression TRUE = def("x", "y").var("x");
	public static final IExpression FALSE = def("x", "y").var("y");
	public static final IExpression ISZERO = def("n").apply(var("n"),
			def("x", FALSE), TRUE);
	public static final IExpression IF = def("c", "x", "y").apply(var("c"),
			var("x"), var("y"));
	public static final IExpression NOT = def("x").apply(var("x"), FALSE, TRUE);
	public static final IExpression AND = def("x", "y").apply(var("x"),
			var("y"), FALSE);
	public static final IExpression OR = def("x", "y").apply(var("x"), TRUE,
			var("y"));
	public static final IExpression PAIR = def("x", "y", "c").apply(var("c"),
			var("x"), var("y"));
	public static final IExpression LEFT = def("x").apply(var("x"), TRUE);
	public static final IExpression RIGHT = def("x").apply(var("x"), FALSE);

	public static final IExpression Y = def("f").apply(
			def("x").apply(var("f"), apply(var("x"), var("x"))),
			def("x").apply(var("f"), apply(var("x"), var("x"))));

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
		if (naturalNumber < 0) {
			throw new IllegalArgumentException();
		}
		IExpression result = var("x");
		for (int index = 0; index < naturalNumber; index++) {
			result = apply(var("f"), result);
		}
		return def(vars("f", "x"), result);
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
