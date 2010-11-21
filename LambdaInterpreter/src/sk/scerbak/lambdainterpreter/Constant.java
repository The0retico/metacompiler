package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Calculus.apply;
import static sk.scerbak.lambdainterpreter.Calculus.def;
import static sk.scerbak.lambdainterpreter.Calculus.var;

/**
 * @author The0retico Predefined constant or internal function.
 */
class Constant extends Symbol implements IExpression {

	/**
	 * Name of this constant.
	 */
	private final String label;

	/**
	 * Expression representing this constant.
	 */
	private final IExpression body;

	private static final IExpression SUCC = def("n", "f", "x").apply(var("f"),
			apply(var("n"), var("f"), var("x")));

	private static final IExpression PLUS = def("m", "n", "f", "x").apply(
			var("m"), var("f"), apply(var("n"), var("f"), var("x")));

	private static final IExpression MULT = def("m", "n", "f").apply(var("n"),
			apply(var("m"), var("f")));

	private static final IExpression TRUE = def("x", "y").var("x");

	private static final IExpression FALSE = def("x", "y").var("y");

	private static final IExpression AND = def("x", "y").apply(var("x"),
			var("y"), FALSE);
	private static final IExpression OR = def("x", "y").apply(var("x"), TRUE,
			var("y"));
	private static final IExpression NOT = def("x")
			.apply(var("x"), FALSE, TRUE);
	private static final IExpression PAIR = def("x", "y", "c").apply(var("c"),
			var("x"), var("y"));
	private static final IExpression LEFT = def("x").apply(var("x"), TRUE);
	private static final IExpression RIGHT = def("x").apply(var("x"), FALSE);
	private static final IExpression PRED = Parser
			.fromString("(n|(f|(x|((n (g|(h|(h (g f))))) (u|x) (u|u)))))");
	private static final IExpression IF = def("c", "x", "y").apply(var("c"),
			var("x"), var("y"));
	private static final IExpression ISZERO = def("n").apply(var("n"),
			def("x", FALSE), TRUE);
	private static final IExpression COMBINATORY = def("f").apply(
			def("x").apply(var("f"), apply(var("x"), var("x"))),
			def("x").apply(var("f"), apply(var("x"), var("x"))));

	/**
	 * @param constantLabel
	 *            uppercase string name of this lambda constant
	 */
	public Constant(final String constantLabel) {
		super();
		label = constantLabel;
		if ("TRUE".equals(constantLabel)) {
			body = TRUE;
		} else if ("FALSE".equals(constantLabel)) {
			body = FALSE;
		} else if ("AND".equals(constantLabel)) {
			body = AND;
		} else if ("OR".equals(constantLabel)) {
			body = OR;
		} else if ("NOT".equals(constantLabel)) {
			body = NOT;
		} else if ("PAIR".equals(constantLabel)) {
			body = PAIR;
		} else if ("LEFT".equals(constantLabel)) {
			body = LEFT;
		} else if ("RIGHT".equals(constantLabel)) {
			body = RIGHT;
		} else if ("Y".equals(constantLabel)) {
			body = COMBINATORY;
		} else if ("IF".equals(constantLabel)) {
			body = IF;
		} else if ("ZERO?".equals(constantLabel)) {
			body = ISZERO;
		} else if ("MULT".equals(constantLabel)) {
			body = MULT;
		} else if ("PLUS".equals(constantLabel)) {
			body = PLUS;
		} else if ("PRED".equals(constantLabel)) {
			body = PRED;
		} else if ("SUCC".equals(constantLabel)) {
			body = SUCC;
		} else {
			body = null;
		}
	}

	@Override
	public void accept(final IVisitor visitor) {
		visitor.visit(this);
		// body.accept(visitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Constant)) {
			return body.equals(obj);
		}
		final Constant other = (Constant) obj;
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean free(final String variable) {
		return false;
	}

	/**
	 * @return the expression
	 */
	public IExpression getExpression() {
		return body;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	@Override
	public boolean isReducible() {
		return body != null;
	}

	@Override
	public IExpression normalForm() {
		return this;
	}

	@Override
	public IExpression oneStepBetaReduce() {
		return this;
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		final IExpression result;
		if (body == null) {
			result = this;
		} else {
			result = body.substitute(variable, expression);
		}
		return result;
	}
}
